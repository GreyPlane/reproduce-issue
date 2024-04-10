name := """websocket-play-tapir"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.13"

val TapirVersion = "1.10.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies ++= Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-core" % TapirVersion,
//  "com.softwaremill.sttp.tapir" %% "tapir-play-server" % "1.10.4+4-10bab7b0+20240410-1810-SNAPSHOT"
  "com.softwaremill.sttp.tapir" %% "tapir-play-server" % TapirVersion
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
