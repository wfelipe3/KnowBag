name := "table-storage"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.microsoft.azure" % "azure-storage" % "5.0.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration"
libraryDependencies += "org.sameersingh.scalaplot" % "scalaplot" % "0.0.4"