name := "play-with-fuures"

version := "1.0"

scalaVersion := "2.11.7"

resolvers ++= Seq("sonatype" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "twitter.com" at "http://maven.twttr.com/")

libraryDependencies += "com.twitter" %% "finagle-httpx" % "6.28.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "3.0"
)