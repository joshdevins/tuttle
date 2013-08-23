organization := "net.joshdevins"

name := "tuttle"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-optimise")

releaseSettings

// main dependencies
// none yet, but will need a few eventually

// test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)
