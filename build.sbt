name := "cognism-template-play"

organization  := "com.cognism"

version := "2.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion  := "2.11.8"

swaggerDomainNameSpaces := Seq("models")

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
  filters,
  ws,
  "joda-time"                 %   "joda-time"                   % "2.9.4"
)

routesGenerator := InjectedRoutesGenerator