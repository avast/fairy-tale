package com.avast.fairytale.monitoring

import com.avast.utils2.Done
import mainecoon.autoFunctorK

import scala.language.higherKinds

@autoFunctorK(autoDerivation = false)
trait Counter[F[_]] {

  def inc: F[Done]
  def inc(n: Long): F[Done]
  def dec: F[Done]
  def dec(n: Int): F[Done]

}
