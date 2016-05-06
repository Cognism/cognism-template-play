name := "cognism-template-play"

organization  := "com.cognism"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion  := "2.11.8"

resolvers ++= Seq()

scalacOptions ++= Seq(
  //Emit warning for usages of deprecated APIs
  "-deprecation",
  //Emit warning for usages of features that should be imported explicitly
  "-feature",
  //Enable additional warnings where generated code depends on assumptions
  "-unchecked",
  //Warn when dead code is identified
  "-Ywarn-dead-code"
)

libraryDependencies ++= Seq(
  cache,
  jdbc,
  filters,
  ws,
  "joda-time"                 %   "joda-time"                   % "2.9.3",
  "org.mongodb"               %%  "casbah"                      % "3.1.1",
  "io.swagger"                %%  "swagger-play2"               % "1.5.2"
)

routesGenerator := InjectedRoutesGenerator