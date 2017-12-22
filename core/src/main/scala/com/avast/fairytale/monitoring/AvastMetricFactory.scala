package com.avast.fairytale.monitoring

import java.time.Duration

import cats.Eval
import com.avast.metrics.api.Timer.TimeContext
import com.avast.metrics.scalaapi.Monitor
import com.avast.utils2.Done

class AvastMetricFactory(monitor: Monitor) extends MetricFactory[Eval] with AutoCloseable {

  override def named(name: String): MetricFactory[Eval] = new AvastMetricFactory(monitor.named(name))

  override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[Eval] =
    new AvastMetricFactory(monitor.named(name1, name2, restOfNames: _*))

  override def meter(name: String): Meter[Eval] = new Meter[Eval] {

    private val underlying = monitor.meter(name)

    override def mark: Eval[Done] = Eval.always {
      underlying.mark()
      Done
    }

    override def mark(n: Long): Eval[Done] = Eval.always {
      underlying.mark(n)
      Done
    }
  }

  override def counter(name: String): Counter[Eval] = new Counter[Eval] {

    private val underlying = monitor.counter(name)

    override def inc: Eval[Done] = Eval.always {
      underlying.inc()
      Done
    }

    override def inc(n: Long): Eval[Done] = Eval.always {
      underlying.inc(n)
      Done
    }

    override def dec: Eval[Done] = Eval.always {
      underlying.dec()
      Done
    }

    override def dec(n: Int): Eval[Done] = Eval.always {
      underlying.dec(n)
      Done
    }
  }

  override def timer(name: String): Timer[Eval] = new Timer[Eval] {

    override type Context = TimeContext

    private val underlying = monitor.timer(name)

    override def start: Eval[Context] = Eval.always(underlying.start())

    override def stop(context: Context): Eval[Duration] = Eval.later(Duration.ofNanos(context.stopAndGetTime()))
  }

  override def histogram(name: String): Histogram[Eval] = new Histogram[Eval] {

    private val underlying = monitor.histogram(name)

    override def update(value: Long): Eval[Done] = Eval.always {
      underlying.update(value)
      Done
    }
  }

  override def close(): Unit = monitor.close()

}
