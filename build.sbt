scalaVersion := "2.12.0-RC2"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.7",
  "org.scalaz.stream" %% "scalaz-stream" % "0.8.5a",
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
