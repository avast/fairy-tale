import sbt._

object Dependencies {
  lazy val metricsLibrary = "com.avast.metrics" %% "metrics-scala" % "2.6.6"
  lazy val metricsJmx = "com.avast.metrics" % "metrics-jmx" % "2.6.6"

  lazy val utilsDone = "com.avast.utils2" %% "utils-done" % "10.0.0"

  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"

  lazy val monixLibrary = "io.monix" %% "monix" % "3.1.0"

  lazy val slf4jLibrary = "org.slf4j" % "slf4j-api" % "1.7.26"

  lazy val simulacrum = "org.typelevel" %% "simulacrum" % "1.0.0"
  lazy val mainecoonCore = "com.kailuowang" %% "mainecoon-core" % "0.6.4"
  lazy val mainecoonMacros = "com.kailuowang" %% "mainecoon-macros" % "0.6.4"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7" % Test
}
