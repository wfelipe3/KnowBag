name := "event-sourcing-hello-world"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalactic" % "scalactic_2.11" % "3.0.0"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0"

libraryDependencies += "net.liftweb" % "lift-json_2.11" % "3.0-M8"

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "io.spray" %% "spray-json" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.slf4j" % "slf4j-simple" % "1.7.21",
    "org.scalaz" %% "scalaz-core" % "7.2.4"
  )
}
