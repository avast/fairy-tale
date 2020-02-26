package com.avast.fairytale.generate

import java.security.SecureRandom
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import cats.tagless.FunctorK
import cats.{Eval, Functor, ~>}


trait IdGenerator[F[_], A] {

  def generate: F[A]

  def map[B](f: A => B)(implicit functor: Functor[F]): IdGenerator[F, B] = new IdGenerator[F, B] {
    override def generate: F[B] = Functor[F].map(IdGenerator.this.generate)(f)
  }

}

object IdGenerator {

  implicit def functorK[A]: FunctorK[IdGenerator[*[_], A]] = new FunctorK[IdGenerator[*[_], A]] {
    def mapK[F[_], G[_]](af: IdGenerator[F, A])(fk: F ~> G): IdGenerator[G, A] = new IdGenerator[G, A]{
      override def generate: G[A] = fk(af.generate)
    }
  } 

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
