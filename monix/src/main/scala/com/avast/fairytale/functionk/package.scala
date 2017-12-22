package com.avast.fairytale

import cats.Eval
import cats.arrow.FunctionK
import monix.eval.Task

package object functionk {

  implicit val evalToTask: FunctionK[Eval, Task] = new FunctionK[Eval, Task] {
    override def apply[A](eval: Eval[A]): Task[A] = Task.pure(eval.value)
  }

}
