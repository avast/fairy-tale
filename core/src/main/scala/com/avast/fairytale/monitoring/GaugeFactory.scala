package com.avast.fairytale.monitoring

import cats.~>
import com.avast.utils2.Done
import mainecoon.FunctorK

import scala.language.higherKinds

trait GaugeFactory[F[_]] {
  def long(name: String): Gauge[F, Long]
  def double(name: String): Gauge[F, Double]
}

object GaugeFactory {
  implicit val functorK: FunctorK[GaugeFactory] = new FunctorK[GaugeFactory] {
    override def mapK[F[_], G[_]](af: GaugeFactory[F])(fk: F ~> G): GaugeFactory[G] = new GaugeFactory[G] {

      override def long(name: String): Gauge[G, Long] = new Gauge[G, Long] {
        private[this] val underlying = af.long(name)
        override def set(value: Long): G[Done] = fk(underlying.set(value))
      }

      override def double(name: String): Gauge[G, Double] = new Gauge[G, Double] {
        private[this] val underlying = af.double(name)
        override def set(value: Double): G[Done] = fk(underlying.set(value))
      }
    }
  }
}
