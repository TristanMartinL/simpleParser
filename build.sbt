ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "simpleParser"
  )


libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0"