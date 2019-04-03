package com.avast.fairytale.logging

import cats.effect.Sync
import com.avast.fairytale.logging.Slf4jLogger.formatMessage
import com.avast.utils2.Done

import scala.language.higherKinds

class Slf4jLogger[F[_]: Sync](slf4j: org.slf4j.Logger) extends Logger[F] {

  private val F = Sync[F]

  override def error(msg: => Message): F[Done] = F.delay {
    if (slf4j.isErrorEnabled) {
      slf4j.error(formatMessage(msg.parts), msg.args.map(_.toString): _*)
    }
    Done
  }

  override def error(ex: Throwable, msg: => Message): F[Done] = F.delay {
    if (slf4j.isErrorEnabled) {
      slf4j.error(formatMessage(msg), ex)
    }
    Done
  }

  override def warn(msg: => Message): F[Done] = F.delay {
    if (slf4j.isWarnEnabled) {
      slf4j.warn(formatMessage(msg.parts), msg.args.map(_.toString): _*)
    }
    Done
  }

  override def warn(ex: Throwable, msg: => Message): F[Done] = F.delay {
    if (slf4j.isWarnEnabled) {
      slf4j.warn(formatMessage(msg), ex)
    }
    Done
  }

  override def info(msg: => Message): F[Done] = F.delay {
    if (slf4j.isInfoEnabled) {
      slf4j.info(formatMessage(msg.parts), msg.args.map(_.toString): _*)
    }
    Done
  }

  override def debug(msg: => Message): F[Done] = F.delay {
    if (slf4j.isDebugEnabled) {
      slf4j.debug(formatMessage(msg.parts), msg.args.map(_.toString): _*)
    }
    Done
  }

  override def debug(ex: Throwable, msg: => Message): F[Done] = F.delay {
    if (slf4j.isDebugEnabled) {
      slf4j.debug(formatMessage(msg), ex)
    }
    Done
  }

  override def trace(msg: => Message): F[Done] = F.delay {
    if (slf4j.isTraceEnabled) {
      slf4j.trace(formatMessage(msg.parts), msg.args.map(_.toString): _*)
    }
    Done
  }

  override def trace(ex: Throwable, msg: => Message): F[Done] = F.delay {
    if (slf4j.isTraceEnabled) {
      slf4j.trace(formatMessage(msg), ex)
    }
    Done
  }

}

object Slf4jLogger {

  private def formatMessage(parts: Seq[String]): String = parts.map(StringContext.treatEscapes).mkString("{}")
  private def formatMessage(msg: Message): String = StringContext(msg.parts: _*).standardInterpolator(identity, msg.args)

}
