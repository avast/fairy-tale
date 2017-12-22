package com.avast.fairytale.monitoring

import simulacrum.typeclass

import scala.language.higherKinds

@typeclass
trait MetricFactory[F[_]] {

  def named(name: String): MetricFactory[F]

  def named(name: String, name2: String, names: String*): MetricFactory[F]

  def meter(name: String): Meter
  def counter(name: String): Counter
  def timer(name: String): Timer
  def timerPair(name: String): TimerPair
  def gauge[A](name: String)(gauge: () => A): Gauge[A]
  def gauge[A](name: String, replaceExisting: Boolean)(gauge: () => A): Gauge[A]
  def histogram(name: String): Histogram

}

object MetricFactory {}
