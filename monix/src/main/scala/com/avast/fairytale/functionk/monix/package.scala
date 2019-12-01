package com.avast.fairytale.functionk

import cats.Eval
import cats.arrow.FunctionK
import _root_.monix.eval.Task

package object monix {
  implicit val evalToTask: FunctionK[Eval, Task] = new FunctionK[Eval, Task] {
    override def apply[A](eval: Eval[A]): Task[A] = Task.from(eval)
  }

}
