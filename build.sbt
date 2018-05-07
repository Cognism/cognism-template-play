name := "cognism-template-play"

version := "3.0.0"

lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayScala)
  .settings(Common.settings: _*)
  .settings(libraryDependencies ++= Common.Dependencies.commonDeps)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SwaggerPlugin)
  .aggregate(common)
  .dependsOn(common)
  .settings(Common.settings: _*)
  .settings(libraryDependencies ++= Common.Dependencies.rootDeps)
  .settings(aggregate in Docker := false)

swaggerDomainNameSpaces := Seq("com.cognism.models")

routesGenerator := InjectedRoutesGenerator

// Docker settings
maintainer in Docker := "ops@cognism.com"

packageName in Docker := "cognism/todo"