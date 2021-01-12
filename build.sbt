organization := "com.idalko.battle_snake"

name := "fw"

version := "0.7.0"

scalaVersion := "2.13.4"
javacOptions ++= Seq("-source", "1.8")
javacOptions ++= Seq("-target", "1.8")

compileOrder := CompileOrder.JavaThenScala

val akkaHttpVersion = "10.2.1"
val akkaVersion = "2.6.10"

enablePlugins(JavaAppPackaging)
mainClass in Compile := Some("com.idalko.battle_snake.fw.Main")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
  "ch.qos.logback"    % "logback-classic"           % "1.2.3",
  "com.google.inject" % "guice"                     % "4.2.3" classifier "no_aop",

  "org.scalactic" %% "scalactic" % "3.2.2",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)
