package com.avast.fairytale.logging

import cats.effect.Sync

import scala.language.higherKinds

class Slf4jLoggerFactory[F[_]: Sync] extends LoggerFactory[F] {

  override def make(name: String): Logger[F] = new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name))

}
