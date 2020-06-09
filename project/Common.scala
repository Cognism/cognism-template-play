import play.sbt.PlayImport._
import sbt.Keys._
import sbt._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "com.cognism",
    scalaVersion := "2.13.2",
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

  object Dependencies {

    val janino = "org.codehaus.janino" % "janino" % "3.1.2"
    val json = "com.typesafe.play" %% "play-json" % "2.9.0"
    val logdnaZileo = "net.zileo" % "logback-logdna" % "1.0.1"

    val commonDeps = Seq(ehcache, ws)

    val rootDeps = Seq(guice, ehcache, filters, ws, specs2 % Test, janino, json, logdnaZileo)

  }
}
