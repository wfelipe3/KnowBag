import sbt.Keys._

name := "image-resizer"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19"

libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"