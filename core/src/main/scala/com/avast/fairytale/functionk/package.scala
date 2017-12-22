package com.avast.fairytale

import cats.arrow.FunctionK
import cats.{Eval, Id}

import scala.concurrent.Future

package object functionk {

  implicit val evalToId: FunctionK[Eval, Id] = new FunctionK[Eval, Id] {
    override def apply[A](eval: Eval[A]): Id[A] = eval.value
  }

  implicit val evalToFuture: FunctionK[Eval, Future] = new FunctionK[Eval, Future] {
    override def apply[A](eval: Eval[A]): Future[A] = Future.successful(eval.value)
  }

}
