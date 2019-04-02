package com.avast.fairytale.monitoring

import java.time.Duration

import cats.effect.Sync
import com.avast.metrics.api.Timer.TimeContext
import com.avast.metrics.scalaapi.Monitor
import com.avast.utils2.Done

import scala.language.higherKinds

class AvastMetricFactory[F[_]: Sync](monitor: Monitor) extends MetricFactory[F] with AutoCloseable {

  private val F = Sync[F]

  override def named(name: String): MetricFactory[F] = new AvastMetricFactory(monitor.named(name))

  override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[F] =
    new AvastMetricFactory(monitor.named(name1, name2, restOfNames: _*))

  override def meter(name: String): Meter[F] = new Meter[F] {

    private val underlying = monitor.meter(name)

    override def mark: F[Done] = F.delay {
      underlying.mark()
      Done
    }

    override def mark(n: Long): F[Done] = F.delay {
      underlying.mark(n)
      Done
    }
  }

  override def counter(name: String): Counter[F] = new Counter[F] {

    private val underlying = monitor.counter(name)

    override def inc: F[Done] = F.delay {
      underlying.inc()
      Done
    }

    override def inc(n: Long): F[Done] = F.delay {
      underlying.inc(n)
      Done
    }

    override def dec: F[Done] = F.delay {
      underlying.dec()
      Done
    }

    override def dec(n: Int): F[Done] = F.delay {
      underlying.dec(n)
      Done
    }
  }

  override def timer(name: String): Timer[F] = new Timer[F] {

    override type Context = TimeContext

    private val underlying = monitor.timer(name)

    override def start: F[Context] = F.delay(underlying.start())

    override def stop(context: Context): F[Duration] = F.delay(Duration.ofNanos(context.stopAndGetTime()))
  }

  override def histogram(name: String): Histogram[F] = new Histogram[F] {

    private val underlying = monitor.histogram(name)

    override def update(value: Long): F[Done] = F.delay {
      underlying.update(value)
      Done
    }
  }

  override def close(): Unit = monitor.close()

}
