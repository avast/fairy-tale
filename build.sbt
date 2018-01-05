import Dependencies._

lazy val root = (project in file(".")).settings(
  name := "fairy-tale",
  publish := {},
  publishLocal := {}
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
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-Xlint", "-Xfatal-warnings", "-Ywarn-value-discard"),
  libraryDependencies ++= Seq(
    cats,
    simulacrum,
    mainecoonMacros,
    scalaTest
  ),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.5"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full)
)
