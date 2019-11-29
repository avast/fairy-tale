package com.avast.fairytale.monitoring

import cats.~>
import com.avast.utils2.Done
import mainecoon.FunctorK

import scala.language.higherKinds

trait GaugeFactory[F[_]] {
  def long: Gauge[F, Long]
  def double: Gauge[F, Double]
}

object GaugeFactory {
  implicit val functorK: FunctorK[GaugeFactory] = new FunctorK[GaugeFactory] {
    override def mapK[F[_], G[_]](af: GaugeFactory[F])(fk: F ~> G): GaugeFactory[G] = new GaugeFactory[G] {
      override def long: Gauge[G, Long] = new Gauge[G, Long] {
        override def set(value: Long): G[Done] = fk(af.long.set(value))
      }

      override def double: Gauge[G, Double] = new Gauge[G, Double] {
        override def set(value: Double): G[Done] = fk(af.double.set(value))
      }
    }
  }
}
