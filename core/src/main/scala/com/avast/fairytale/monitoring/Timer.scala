package com.avast.fairytale.monitoring

import cats.Monad

import scala.language.higherKinds

trait Timer[F[_]] {

  def time[A](block: => A): F[A]
  def time[A](block: => F[A])(implicit monad: Monad[F]): F[A]

}
