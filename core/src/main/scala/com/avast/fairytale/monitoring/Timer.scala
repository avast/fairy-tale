package com.avast.fairytale.monitoring

import java.time.Duration

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import mainecoon.autoFunctorK

import scala.language.higherKinds

@autoFunctorK(autoDerivation = false)
trait Timer[F[_]] {

  type Context

  def start: F[Context]

  def stop(context: Context): F[Duration]

}

object Timer {

  object dsl {

    implicit class TimerOps[F[_]](val timer: Timer[F]) extends AnyVal {

      def time[A](block: F[A])(implicit monad: Monad[F]): F[A] = {
        for {
          context <- timer.start
          result <- block
          _ <- timer.stop(context)
        } yield result
      }

    }
  }

}
