package com.avast.fairytale.monitoring

import com.avast.utils2.Done
import mainecoon.autoFunctorK

import scala.language.higherKinds

@autoFunctorK(autoDerivation = false)
trait Meter[F[_]] {

  def mark: F[Done]
  def mark(n: Long): F[Done]

}
