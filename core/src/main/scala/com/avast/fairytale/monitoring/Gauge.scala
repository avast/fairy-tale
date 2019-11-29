package com.avast.fairytale.monitoring

import com.avast.utils2.Done

import scala.language.higherKinds

trait Gauge[F[_], T] {
  def set(value: T): F[Done]
}
