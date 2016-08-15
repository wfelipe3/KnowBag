name := "akka-basics"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.8",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.8",
  "com.typesafe.akka" %% "akka-remote" % "2.4.8",
  "com.typesafe.akka" %% "akka-agent" % "2.4.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.8" % "test"
)