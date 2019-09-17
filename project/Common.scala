import play.sbt.PlayImport._
import sbt.Keys._
import sbt._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "com.cognism",
    scalaVersion := "2.12.6",
    scalacOptions ++= Seq(
      //Emit warning for usages of deprecated APIs
      "-deprecation",
      //Emit warning for usages of features that should be imported explicitly
      "-feature",
      //Enable additional warnings where generated code depends on assumptions
      "-unchecked",
      //Warn when dead code is identified
      "-Ywarn-dead-code",
      "-language:postfixOps"
    )
  )

  object Dependencies{
    
    val papertrail = "com.papertrailapp" % "logback-syslog4j" % "1.0.0"
    val janino = "org.codehaus.janino" % "janino" % "3.0.11"
    val json = "com.typesafe.play" %% "play-json" % "2.7.3"

    val commonDeps = Seq(ehcache, ws)

    val rootDeps = Seq(guice, ehcache, filters, ws, specs2 % Test, papertrail, janino, json)

  }
}