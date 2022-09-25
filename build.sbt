import scala.language.postfixOps

name := "Everi"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  "org.jogamp.jogl" %  "jogl-all-main" % "2.3.2",
  "org.processing" %  "core" % "3.3.5" withSources(),
  "org.jetbrains.kotlin" %  "kotlin-stdlib" % "1.3.72",
  "org.jetbrains.kotlin" %  "kotlin-test" % "1.7.10" % "test",
  "org.jbox2d" %  "jbox2d-library" % "2.2.1.1" withSources(),
  "org.scalatest"     %% "scalatest"   % "3.0.3" % Test withSources(),
  "junit"             %  "junit"       % "4.13"  % Test,
  "org.scala-lang" % "scala-xml" % "2.11.0-M4"
)