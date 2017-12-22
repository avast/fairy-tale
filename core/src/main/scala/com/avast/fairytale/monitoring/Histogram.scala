package com.avast.fairytale.monitoring

import com.avast.utils2.Done
import mainecoon.autoFunctorK

import scala.language.higherKinds

@autoFunctorK(autoDerivation = false)
trait Histogram[F[_]] {

  def update(value: Long): F[Done]

}
