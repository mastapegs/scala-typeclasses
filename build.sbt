import Dependencies._

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val typeclasses = (project in file("typeclasses"))
  .settings(
    name := "typeclasses",
    libraryDependencies ++= Seq(
      munit % Test,
      scalacheck % Test,
      munit_scalacheck % Test
    )
  )

lazy val exampleApp = (project in file("example-app"))
  .dependsOn(typeclasses)
  .settings(
    name := "example-app"
  )

lazy val root = (project in file("."))
  .aggregate(typeclasses, exampleApp)
  .settings(
    name := "typeclasses-sandbox"
  )
