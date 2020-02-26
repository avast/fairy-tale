package com.avast.fairytale.monitoring

import cats.tagless.autoFunctorK
import com.avast.utils2.Done



@autoFunctorK(autoDerivation = false)
trait Counter[F[_]] {

  def inc: F[Done]
  def inc(n: Long): F[Done]
  def dec: F[Done]
  def dec(n: Int): F[Done]

}
