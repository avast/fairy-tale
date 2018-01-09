package com.avast.fairytale.monitoring

import cats.{Eval, Id}
import cats.arrow.FunctionK
import com.avast.fairytale.monitoring.Timer.dsl._
import com.avast.metrics.api.Naming
import com.avast.metrics.dropwizard.{JmxMetricsMonitor, TreeObjectNameFactory}
import com.avast.metrics.scalaapi.Monitor
import com.avast.utils2.Done
import com.codahale.metrics.MetricRegistry
import org.scalatest.FunSuite

import scala.language.higherKinds

class AvastMetricFactoryTest extends FunSuite {

  test("basic functionality of metrics") {
    val registry = new MetricRegistry
    val target =
      new AvastMetricFactory(Monitor(new JmxMetricsMonitor(TreeObjectNameFactory.getInstance, "test", registry, Naming.defaultNaming)))
    try {
      val meter = target.meter("meter")
      val counter = target.counter("counter")
      val histogram = target.histogram("histogram")
      val timer1 = target.timer("timer1")
      val timer2 = target.timer("timer2")

      val result =
        timer1.time {
          for {
            ctx <- timer2.start
            _ <- meter.mark
            _ <- counter.inc(2)
            _ <- histogram.update(100)
            _ <- timer2.stop(ctx)
          } yield Done
        }

      assert(registry.getMeters.get("meter").getCount === 0)
      assert(registry.getCounters.get("counter").getCount === 0)
      assert(registry.getHistograms.get("histogram").getSnapshot.getValues.isEmpty)
      assert(registry.getTimers.get("timer1").getSnapshot.getValues.isEmpty)
      assert(registry.getTimers.get("timer2").getSnapshot.getValues.isEmpty)

      result.value // run Eval

      assert(registry.getMeters.get("meter").getCount === 1)
      assert(registry.getCounters.get("counter").getCount === 2)
      assert(registry.getHistograms.get("histogram").getSnapshot.getValues.head === 100)
      assert(registry.getTimers.get("timer1").getSnapshot.getValues.head > 0)
      assert(registry.getTimers.get("timer2").getSnapshot.getValues.head > 0)
    } finally {
      target.close()
    }
  }

  test("FunctorK works for metrics") {
    val monitor = Monitor(new JmxMetricsMonitor("test"))
    try {
      val metricFactory: MetricFactory[Eval] = new AvastMetricFactory(monitor)

      val targetMeter = monitor.named("a").named("b").meter("x")

      def test[F[_]](factory: MetricFactory[F]): Meter[F] = {
        factory.named("a").named("b").meter("x")
      }

      test(metricFactory).mark.value
      assert(targetMeter.count === 1)

      def lift[A](v: Eval[A]): A = v.value
      val idMetricFactory = MetricFactory.mapK(metricFactory)(FunctionK.lift[Eval, Id](lift))

      test(idMetricFactory).mark
      assert(targetMeter.count === 2)
    }
    finally {
      monitor.close()
    }
  }

}
