package com.avast.fairytale.time

import java.time.Instant

import cats.Eval

class JavaClock(clock: java.time.Clock) extends Clock[Eval] {

  override def instant: Eval[Instant] = Eval.always {
    clock.instant()
  }

}
