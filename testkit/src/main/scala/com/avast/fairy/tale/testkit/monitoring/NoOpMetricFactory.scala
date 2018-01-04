package com.avast.fairy.tale.testkit.monitoring

import java.time.Duration

import cats.Id
import com.avast.fairytale.monitoring._
import com.avast.utils2.Done

object NoOpMetricFactory extends MetricFactory[Id]{
  override def named(name: String): MetricFactory[Id] = this

  override def named(name1: String, name2: String, restOfNames: String*): MetricFactory[Id] = this

  override def meter(name: String): Meter[Id] = NoOpMeter

  override def counter(name: String): Counter[Id] = NoOpCounter

  override def timer(name: String): Timer[Id] = NoOpTimer

  override def histogram(name: String): Histogram[Id] = NoOpHistogram
}

private object NoOpMeter extends Meter[Id] {
  override def mark: Id[Done] = Done

  override def mark(n: Long): Id[Done] = Done
}

private object NoOpCounter extends Counter[Id] {
  override def inc: Id[Done] = Done

  override def inc(n: Long): Id[Done] = Done

  override def dec: Id[Done] = Done

  override def dec(n: Int): Id[Done] = Done
}

private object NoOpTimer extends Timer[Id] {
  type Context = Long

  override def start: Id[Context] = System.nanoTime()

  override def stop(context: Context): Id[Duration] = Duration.ofNanos(System.nanoTime() - context)
}

private object NoOpHistogram extends Histogram[Id] {
  override def update(value: Long): Id[Done] = Done
}