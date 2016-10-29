scalaVersion := "2.12.0-RC2"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-concurrent" % "7.2.7",
  "org.scalacheck" %% "scalacheck" % "1.13.3" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
