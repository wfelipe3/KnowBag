name := "reactive-rabbit-mq"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1"

libraryDependencies += "io.scalac" %% "reactive-rabbit" % "1.1.4"

libraryDependencies += "com.rabbitmq" % "amqp-client" % "4.0.0"

libraryDependencies += "io.reactivex" % "rxscala_2.11" % "0.26.5"


val akkaVersion = "2.4.16"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion
)
