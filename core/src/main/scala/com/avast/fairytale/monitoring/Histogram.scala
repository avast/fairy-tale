package com.avast.fairytale.monitoring

import cats.tagless.autoFunctorK
import com.avast.utils2.Done



@autoFunctorK(autoDerivation = false)
trait Histogram[F[_]] {

  def update(value: Long): F[Done]

}
