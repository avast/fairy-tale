package com.avast.fairytale.logging

import cats._
import mainecoon.FunctorK
import simulacrum.typeclass

import scala.language.{higherKinds, implicitConversions}
import scala.reflect.ClassTag

@typeclass
trait LoggerFactory[F[_]] {

  def make(name: String): Logger[F]

  def make[A](implicit classTag: ClassTag[A]): Logger[F] = make(classTag.runtimeClass.getName)

}

object LoggerFactory {

  def mapK[F[_], G[_]](originalFactory: LoggerFactory[F])(fk: F ~> G): LoggerFactory[G] = { name =>
    Logger.mapK(originalFactory.make(name))(fk)
  }

  implicit val functorKForLoggerFactory: FunctorK[LoggerFactory] = new FunctorK[LoggerFactory] {
    override def mapK[F[_], G[_]](af: LoggerFactory[F])(fk: ~>[F, G]): LoggerFactory[G] = LoggerFactory.mapK(af)(fk)
  }

  def slf4j: LoggerFactory[Eval] = Slf4jLoggerFactory

}
