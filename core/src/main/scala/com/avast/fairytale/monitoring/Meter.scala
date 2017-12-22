package com.avast.fairytale.monitoring

import com.avast.utils2.Done

import scala.language.higherKinds

trait Meter[F[_]] {

  def mark: F[Done]
  def mark(n: Long): F[Done]

}
