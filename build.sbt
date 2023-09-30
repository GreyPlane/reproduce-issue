ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "natural-dryad",
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "com.google.protobuf" % "protobuf-java" % "3.21.9",
      "com.google.protobuf" % "protobuf-java-util" % "3.21.9"
    ),
    Compile / PB.targets := Seq(
      PB.gens.java -> (Compile / sourceManaged).value,
      scalapb.gen(grpc = false) -> (Compile / sourceManaged).value
    )
  )
