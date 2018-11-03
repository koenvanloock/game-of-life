import sbt.Keys.libraryDependencies

name := "game_of_life"

version := "1.0"

scalaVersion := "2.12.1"

resourceDirectory in Compile := baseDirectory.value / "resources"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  "org.specs2" %% "specs2-core" % "4.3.5" % Test,
  "org.specs2" %% "specs2-junit" % "4.3.5" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test,
  "junit" % "junit" % "4.10" % Test
)
