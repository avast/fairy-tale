package com.avast.fairytale.time

import java.time.Instant

import cats.Eval
import mainecoon.autoFunctorK
import simulacrum.typeclass

import scala.language.{higherKinds, implicitConversions}

@typeclass
@autoFunctorK(autoDerivation = false)
trait Clock[F[_]] {

  def instant: F[Instant]

}

object Clock {

  def apply(javaClock: java.time.Clock): Clock[Eval] = new JavaClock(javaClock)

  def systemUtc: Clock[Eval] = new JavaClock(java.time.Clock.systemUTC)

}
