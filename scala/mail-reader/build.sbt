name := "mail-reader"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies += "javax.mail" % "mail" % "1.4"

libraryDependencies += "com.google.apis" % "google-api-services-gmail" % "v1-rev81-1.23.0"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.23.0"
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.23.0"
