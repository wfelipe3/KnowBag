name := "distributed-load-tester"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
libraryDependencies += "org.scalaz" % "scalaz-core_2.11" % "7.3.0-M6"

libraryDependencies += "net.liftweb" % "lift-json_2.11" % "3.0-M8"
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
