package com.avast.fairytale.monitoring

import com.avast.utils2.Done



trait Gauge[F[_], T] {
  def set(value: T): F[Done]
}
