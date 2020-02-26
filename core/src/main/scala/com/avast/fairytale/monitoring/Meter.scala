package com.avast.fairytale.monitoring

import cats.tagless.autoFunctorK
import com.avast.utils2.Done



@autoFunctorK(autoDerivation = false)
trait Meter[F[_]] {

  def mark: F[Done]
  def mark(n: Long): F[Done]

}
