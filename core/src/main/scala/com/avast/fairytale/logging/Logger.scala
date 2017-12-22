package com.avast.fairytale.logging

import com.avast.utils2.Done
import mainecoon.autoFunctorK
import simulacrum.typeclass

import scala.language.{higherKinds, implicitConversions}

@typeclass
@autoFunctorK(autoDerivation = false)
trait Logger[F[_]] {

  def error(msg: => Message): F[Done]
  def error(ex: Throwable, msg: => Message): F[Done]

  def warn(msg: => Message): F[Done]
  def warn(ex: Throwable, msg: => Message): F[Done]

  def info(msg: => Message): F[Done]

  def debug(msg: => Message): F[Done]
  def debug(ex: Throwable, msg: => Message): F[Done]

  def trace(msg: => Message): F[Done]
  def trace(ex: Throwable, msg: => Message): F[Done]

}

object Logger
