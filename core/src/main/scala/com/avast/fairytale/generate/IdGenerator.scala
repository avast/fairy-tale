package com.avast.fairytale.generate

import java.security.SecureRandom
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import cats.{Eval, Functor}
import mainecoon.autoFunctorK

import scala.language.higherKinds

@autoFunctorK(autoDerivation = false)
trait IdGenerator[F[_], A] {

  def generate: F[A]

  def map[B](f: A => B)(implicit functor: Functor[F]): IdGenerator[F, B] = new IdGenerator[F, B] {
    override def generate: F[B] = Functor[F].map(IdGenerator.this.generate)(f)
  }

}

object IdGenerator {

  val uuid: IdGenerator[Eval, UUID] = new IdGenerator[Eval, UUID] {
    override def generate: Eval[UUID] = Eval.always(UUID.randomUUID())
  }

  val long: IdGenerator[Eval, Long] = new IdGenerator[Eval, Long] {
    override def generate: Eval[Long] = Eval.always(ThreadLocalRandom.current.nextLong())
  }

  def long(rnd: SecureRandom): IdGenerator[Eval, Long] = new IdGenerator[Eval, Long] {
    override def generate: Eval[Long] = Eval.always(rnd.nextLong())
  }

}
