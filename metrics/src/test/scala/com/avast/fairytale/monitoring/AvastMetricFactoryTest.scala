package com.avast.fairytale.monitoring

import cats.effect.IO
import com.avast.fairytale.monitoring.Timer.dsl._
import com.avast.metrics.api.Naming
import com.avast.metrics.dropwizard.{JmxMetricsMonitor, TreeObjectNameFactory}
import com.avast.metrics.scalaapi.Monitor
import com.avast.utils2.Done
import com.codahale.metrics.MetricRegistry
import org.scalatest.FunSuite

class AvastMetricFactoryTest extends FunSuite {

  test("basic functionality of metrics") {
    val registry = new MetricRegistry
    val target =
      new AvastMetricFactory[IO](Monitor(new JmxMetricsMonitor(TreeObjectNameFactory.getInstance, "test", registry, Naming.defaultNaming)))
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

      result.unsafeRunSync()

      assert(registry.getMeters.get("meter").getCount === 1)
      assert(registry.getCounters.get("counter").getCount === 2)
      assert(registry.getHistograms.get("histogram").getSnapshot.getValues.head === 100)
      assert(registry.getTimers.get("timer1").getSnapshot.getValues.head > 0)
      assert(registry.getTimers.get("timer2").getSnapshot.getValues.head > 0)
    } finally {
      target.close()
    }
  }

}
