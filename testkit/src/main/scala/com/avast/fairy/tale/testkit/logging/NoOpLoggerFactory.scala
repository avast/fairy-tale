package com.avast.fairy.tale.testkit.logging

import cats.Id
import com.avast.fairytale.logging.{Logger, LoggerFactory, Message}
import com.avast.utils2.Done

object NoOpLoggerFactory extends LoggerFactory[Id]{
  override def make(name: String): Logger[Id] = NoOpLogger
}

private object NoOpLogger extends Logger[Id] {
  override def error(msg: => Message): Id[Done] = Done

  override def error(ex: Throwable, msg: => Message): Id[Done] = Done

  override def warn(msg: => Message): Id[Done] = Done

  override def warn(ex: Throwable, msg: => Message): Id[Done] = Done

  override def info(msg: => Message): Id[Done] = Done

  override def debug(msg: => Message): Id[Done] = Done

  override def debug(ex: Throwable, msg: => Message): Id[Done] = Done

  override def trace(msg: => Message): Id[Done] = Done

  override def trace(ex: Throwable, msg: => Message): Id[Done] = Done
}
