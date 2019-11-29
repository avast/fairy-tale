package com.avast.fairy.tale.testkit.monitoring

import java.time.Duration

import cats.effect.Sync
import com.avast.fairytale.monitoring._
import com.avast.utils2.Done

import scala.language.higherKinds

class NoOpMetricFactory[F[_]: Sync] extends MetricFactory[F] {

  override def named(name: String): MetricFactory[F] = this

  override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[F] = this

  override def meter(name: String): Meter[F] = new NoOpMeter

  override def counter(name: String): Counter[F] = new NoOpCounter

  override def timer(name: String): Timer[F] = new NoOpTimer

  override def histogram(name: String): Histogram[F] = new NoOpHistogram

  override def gauge(name: String): GaugeFactory[F] = new NoOpGaugeFactory
}

private class NoOpMeter[F[_]: Sync] extends Meter[F] {

  private val F = Sync[F]

  override def mark: F[Done] = F.delay(Done)

  override def mark(n: Long): F[Done] = F.delay(Done)

}

private class NoOpCounter[F[_]: Sync] extends Counter[F] {

  private val F = Sync[F]

  override def inc: F[Done] = F.delay(Done)

  override def inc(n: Long): F[Done] = F.delay(Done)

  override def dec: F[Done] = F.delay(Done)

  override def dec(n: Int): F[Done] = F.delay(Done)

}

private class NoOpTimer[F[_]: Sync] extends Timer[F] {

  private val F = Sync[F]

  type Context = Long

  override def start: F[Context] = F.delay(System.nanoTime())

  override def stop(context: Context): F[Duration] = F.delay(Duration.ofNanos(System.nanoTime() - context))

}

private class NoOpHistogram[F[_]: Sync] extends Histogram[F] {

  private val F = Sync[F]

  override def update(value: Long): F[Done] = F.delay(Done)

}

private class NoOpGaugeFactory[F[_]: Sync] extends GaugeFactory[F] {
  override def long: Gauge[F, Long] = new NoOpGauge

  override def double: Gauge[F, Double] = new NoOpGauge
}

private class NoOpGauge[F[_]: Sync, T] extends Gauge[F, T] {
  override def set(value: T): F[Done] = Sync[F].delay(Done)
}
