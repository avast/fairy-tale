package com.avast.fairytale.monitoring

import cats.~>
import mainecoon.FunctorK
import simulacrum.typeclass

import scala.language.{higherKinds, implicitConversions}

@typeclass
trait MetricFactory[F[_]] {

  def named(name: String): MetricFactory[F]
  def named(name1: String, name2: String, restOfNames: String*): MetricFactory[F]

  def meter(name: String): Meter[F]
  def counter(name: String): Counter[F]
  def timer(name: String): Timer[F]
  def histogram(name: String): Histogram[F]

  def gauge: GaugeFactory[F]

}

object MetricFactory {

  def mapK[F[_], G[_]](originalFactory: MetricFactory[F])(fk: F ~> G): MetricFactory[G] = new MetricFactory[G] {

    private class Proxy(namedFactory: MetricFactory[F]) extends MetricFactory[G] {
      override def named(name: String): MetricFactory[G] = MetricFactory.mapK(namedFactory.named(name))(fk)

      override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[G] =
        MetricFactory.mapK(namedFactory.named(name1, name2, restOfNames: _*))(fk)

      override def meter(name: String): Meter[G] = FunctorK[Meter].mapK(namedFactory.meter(name))(fk)

      override def counter(name: String): Counter[G] = FunctorK[Counter].mapK(namedFactory.counter(name))(fk)

      override def timer(name: String): Timer[G] = FunctorK[Timer].mapK(namedFactory.timer(name))(fk)

      override def histogram(name: String): Histogram[G] = FunctorK[Histogram].mapK(namedFactory.histogram(name))(fk)

      override def gauge: GaugeFactory[G] = FunctorK[GaugeFactory].mapK(namedFactory.gauge)(fk)
    }

    override def named(name: String): MetricFactory[G] = {
      val namedFactory = originalFactory.named(name)
      new Proxy(namedFactory)
    }

    override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[G] = {
      val namedFactory = originalFactory.named(name1, name2, restOfNames: _*)
      new Proxy(namedFactory)
    }

    override def meter(name: String): Meter[G] = FunctorK[Meter].mapK(originalFactory.meter(name))(fk)

    override def counter(name: String): Counter[G] = FunctorK[Counter].mapK(originalFactory.counter(name))(fk)

    override def histogram(name: String): Histogram[G] = FunctorK[Histogram].mapK(originalFactory.histogram(name))(fk)

    override def timer(name: String): Timer[G] = FunctorK[Timer].mapK(originalFactory.timer(name))(fk)

    override def gauge: GaugeFactory[G] = FunctorK[GaugeFactory].mapK(originalFactory.gauge)(fk)
  }

  implicit val functorKForMetricFactory: FunctorK[MetricFactory] = new FunctorK[MetricFactory] {
    override def mapK[F[_], G[_]](af: MetricFactory[F])(fk: F ~> G): MetricFactory[G] = MetricFactory.mapK(af)(fk)
  }

}
