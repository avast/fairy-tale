package com.avast.fairytale.logging

import cats.Eval

object Slf4jLoggerFactory extends LoggerFactory[Eval] {

  override def make(name: String): Logger[Eval] = new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name))

}
