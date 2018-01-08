import sbt._

object Dependencies {
  lazy val metricsLibrary = "com.avast.metrics" %% "metrics-scala" % "2.3.3"
  lazy val metricsJmx = "com.avast.metrics" % "metrics-jmx" % "2.3.3"

  lazy val utilsDone = "com.avast.utils2" %% "utils-done" % "10.0.0"

  lazy val cats = "org.typelevel" %% "cats-core" % "1.0.1"

  lazy val monixLibrary = "io.monix" %% "monix" % "3.0.0-M3"

  lazy val slf4jLibrary = "org.slf4j" % "slf4j-api" % "1.7.25"

  lazy val simulacrum = "com.github.mpilquist" %% "simulacrum" % "0.11.0"
  lazy val mainecoonMacros = "com.kailuowang" %% "mainecoon-macros" % "0.6.0"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
}
