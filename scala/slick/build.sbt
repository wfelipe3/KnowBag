name := "slick"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.4.189",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.almoehi" %% "reactive-docker" % "0.1-SNAPSHOT"
)


