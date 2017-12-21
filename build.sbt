import Dependencies._

lazy val root = (project in file(".")).settings(
  name := "scala-fp-toolbox",
  publish := {},
  publishLocal := {}
).aggregate(core, monix)

lazy val core = (project in file("core")).settings(
  commonSettings,
  scalaSettings,
  name := "scala-fp-toolbox-core"
)

lazy val monix = (project in file("monix")).settings(
  commonSettings,
  scalaSettings,
  name := "scala-fp-toolbox-monix"
).dependsOn(core)

lazy val commonSettings = Seq(
  organization := "com.avast",
  version := sys.env.getOrElse("VERSION", "0.1-SNAPSHOT"),
  description := "Toolbox for functional programming in Scala",
  licenses += "MIT" -> url("https://github.com/avast/scala-fp-toolbox/blob/master/LICENSE"),

  publishArtifact in Test := false,

  bintrayOrganization := Some("avast"),
  bintrayPackage := "scala-fp-toolbox",
  bintrayPackageLabels := Seq("fp", "functional programming", "scala", "cats", "toolbox", "utils"),

  pomExtra :=
    <scm>
      <url>git@github.com:avast/fp-toolbox.git</url>
      <connection>scm:git:git@github.com:avast/fp-toolbox.git</connection>
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
  libraryDependencies += scalaTest
)
