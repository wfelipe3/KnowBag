name := "freemonad-cats"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2"
)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.github.scopt" % "scopt_2.12" % "3.6.0"
