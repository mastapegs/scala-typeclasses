import Dependencies._

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

scalafmtOnCompile := true
scalafixOnCompile := true

inThisBuild(
  List(
    scalaVersion := "2.13.12",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)

val options = Seq(
  "-deprecation", // Warns about deprecated APIs
  "-feature", // Warns about misused language features
  "-unchecked", // More warnings when compiling
  "-Xlint", // Enable additional warnings
  "-Ywarn-dead-code", // Warn when dead code is detected
  "-Ywarn-unused", // Warn when imports or other elements are unused
  "-Ywarn-unused:imports", // Specifically warn about unused imports
  "-Ywarn-unused:patvars", // Warn when pattern variables are unused
  "-Ywarn-unused:privates", // Warn when private fields or methods are unused
  "-Ywarn-unused:locals", // Warn when local variables are unused
  "-Ywarn-unused:explicits", // Warn when explicit parameter lists are unused
  "-Xplugin-require:semanticdb", // Enable SemanticDB generation for all projects
  "-Yrangepos" // // Minimum Scala version supported by SemanticDB
)

scalacOptions ++= options

lazy val root = (project in file("."))
  .settings(
    name := "sandbox",
    addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.9.9" cross CrossVersion.full),
    inConfig(Test)(
      Defaults.testSettings ++ Seq(
        scalacOptions ++= options
      )
    ),
    libraryDependencies ++= Seq(
      munit % Test,
      scalacheck % Test,
      munit_scalacheck % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
