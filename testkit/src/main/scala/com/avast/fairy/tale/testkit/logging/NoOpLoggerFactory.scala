package com.avast.fairy.tale.testkit.logging

import cats.effect.Sync
import com.avast.fairytale.logging.{Logger, LoggerFactory, Message}
import com.avast.utils2.Done

import scala.language.higherKinds

class NoOpLoggerFactory[F[_]: Sync] extends LoggerFactory[F] {
  override def make(name: String): Logger[F] = new NoOpLogger
}

private class NoOpLogger[F[_]: Sync] extends Logger[F] {

  private val F = Sync[F]

  override def error(msg: => Message): F[Done] = F.delay(Done)

  override def error(ex: Throwable, msg: => Message): F[Done] = F.delay(Done)

  override def warn(msg: => Message): F[Done] = F.delay(Done)

  override def warn(ex: Throwable, msg: => Message): F[Done] = F.delay(Done)

  override def info(msg: => Message): F[Done] = F.delay(Done)

  override def debug(msg: => Message): F[Done] = F.delay(Done)

  override def debug(ex: Throwable, msg: => Message): F[Done] = F.delay(Done)

  override def trace(msg: => Message): F[Done] = F.delay(Done)

  override def trace(ex: Throwable, msg: => Message): F[Done] = F.delay(Done)

}
