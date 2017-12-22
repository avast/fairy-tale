package com.avast.fairytale

import scala.concurrent.{ExecutionContext, Future}

package object functionk {

  implicit val evalToId: FunctionK[Eval, Id] = new FunctionK[Eval, Id] {
    override def apply[A](eval: Eval[A]): Id[A] = eval.value
  }

  implicit def evalToFuture(implicit ec: ExecutionContext): FunctionK[Eval, Future] = new FunctionK[Eval, Future] {
    override def apply[A](eval: Eval[A]): Future[A] = Future(eval.value)
  }

}
