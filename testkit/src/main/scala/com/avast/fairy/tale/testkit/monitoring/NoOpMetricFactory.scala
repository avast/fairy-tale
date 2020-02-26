package com.avast.fairy.tale.testkit.monitoring

import java.time.Duration

import cats.Applicative
import com.avast.fairytale.monitoring._
import com.avast.utils2.Done



class NoOpMetricFactory[F[_]: Applicative] extends MetricFactory[F] {

  override def named(name: String): MetricFactory[F] = this

  override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[F] = this

  override def meter(name: String): Meter[F] = new NoOpMeter

  override def counter(name: String): Counter[F] = new NoOpCounter

  override def timer(name: String): Timer[F] = new NoOpTimer

  override def histogram(name: String): Histogram[F] = new NoOpHistogram

  override def gauge: GaugeFactory[F] = new NoOpGaugeFactory
}

private class NoOpMeter[F[_]: Applicative] extends Meter[F] {

  private val F = Applicative[F]

  override def mark: F[Done] = F.pure(Done)

  override def mark(n: Long): F[Done] = F.pure(Done)

}

private class NoOpCounter[F[_]: Applicative] extends Counter[F] {

  private val F = Applicative[F]

  override def inc: F[Done] = F.pure(Done)

  override def inc(n: Long): F[Done] = F.pure(Done)

  override def dec: F[Done] = F.pure(Done)

  override def dec(n: Int): F[Done] = F.pure(Done)

}

private class NoOpTimer[F[_]: Applicative] extends Timer[F] {

  private val F = Applicative[F]

  type Context = Long

  override def start: F[Context] = F.pure(System.nanoTime())

  override def stop(context: Context): F[Duration] = F.pure(Duration.ofNanos(System.nanoTime() - context))

}

private class NoOpHistogram[F[_]: Applicative] extends Histogram[F] {

  private val F = Applicative[F]

  override def update(value: Long): F[Done] = F.pure(Done)

}

private class NoOpGaugeFactory[F[_]: Applicative] extends GaugeFactory[F] {
  override def long(name: String): Gauge[F, Long] = new NoOpGauge

  override def double(name: String): Gauge[F, Double] = new NoOpGauge
}

private class NoOpGauge[F[_]: Applicative, T] extends Gauge[F, T] {
  override def set(value: T): F[Done] = Applicative[F].pure(Done)
}
