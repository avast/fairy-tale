package com.avast.fairytale.monitoring

import cats.Eval
import com.avast.metrics.scalaapi.Monitor

class AvastMetricsFactory(monitor: Monitor) extends MetricFactory[Eval] {

  monitor.

}
