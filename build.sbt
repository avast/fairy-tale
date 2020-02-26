import Dependencies._

lazy val root = (project in file(".")).settings(
  name := "fairy-tale",
  publish := {},
  publishLocal := {},
  crossScalaVersions := Nil
).aggregate(core, metrics, monix, slf4j, testkit)

lazy val core = (project in file("core")).settings(
  commonSettings,
  scalaSettings,
  name := "fairy-tale-core",
  libraryDependencies ++= Seq(
    utilsDone
  )
)

lazy val metrics = (project in file("metrics")).settings(
  commonSettings,
  scalaSettings,
  name := "fairy-tale-metrics",
  libraryDependencies ++= Seq(
    metricsLibrary,
    metricsJmx % Test
  )
).dependsOn(core)

lazy val monix = (project in file("monix")).settings(
  commonSettings,
  scalaSettings,
  name := "fairy-tale-monix",
  libraryDependencies ++= Seq(
    monixLibrary
  )
).dependsOn(core)

lazy val slf4j = (project in file("slf4j")).settings(
  commonSettings,
  scalaSettings,
  name := "fairy-tale-slf4j",
  libraryDependencies ++= Seq(
    slf4jLibrary
  )
).dependsOn(core)

lazy val testkit = (project in file("testkit")).settings(
  commonSettings,
  scalaSettings,
  name := "fairy-tale-testkit"
).dependsOn(core)

val CompileTime = config("compile-time").hide

lazy val commonSettings = Seq(
  organization := "com.avast.fairytale",
  version := sys.env.getOrElse("VERSION", "0.1-SNAPSHOT"),
  description := "Toolbox for functional programming in Scala using the Finally Tagless approach.",
  licenses += "MIT" -> url("https://github.com/avast/fairy-tale/blob/master/LICENSE"),

  resolvers += Resolver.jcenterRepo,

  publishArtifact in Test := false,
  publishArtifact in(Compile, packageDoc) := false,
  sources in(Compile, doc) := Seq.empty,

  bintrayOrganization := Some("avast"),
  bintrayPackage := "fairy-tale",
  bintrayPackageLabels := Seq("fp", "functional programming", "scala", "cats", "toolbox", "utils", "finally tagless", "tagless final"),

  ivyConfigurations += CompileTime,
  unmanagedClasspath in Compile ++= update.value.select(configurationFilter(CompileTime.name)),

  pomExtra :=
    <scm>
      <url>git@github.com:avast/fairy-tale.git</url>
      <connection>scm:git:git@github.com:avast/fairy-tale.git</connection>
    </scm>
      <developers>
        <developer>
          <id>avast</id>
          <name>Jakub Janecek, Avast Software s.r.o.</name>
          <url>https://www.avast.com</url>
        </developer>
      </developers>
)

lazy val scalaSettings = Seq(
  scalaVersion := "2.12.8",
  crossScalaVersions := List("2.12.8", "2.13.1"),
  scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-Xlint", "-Xfatal-warnings", "-Ywarn-value-discard", "-language:higherKinds"),
  libraryDependencies ++= Seq(
    cats,
    catsEffect,
    simulacrum,
    mainecoonCore,
    scalaTest,
    silencerLib
  ),
  addCompilerPlugin(silencerPlugin),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full),
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 13 => "-Ymacro-annotations" :: Nil
      case _ => Nil
    }
  },
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 13 => Nil
      case _ => compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full) :: Nil
    }
  }
)
