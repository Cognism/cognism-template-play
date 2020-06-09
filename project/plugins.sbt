logLevel := Level.Warn

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.1")

// iHeart Swagger Plugin
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.9.1-PLAY2.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.3")

addSbtPlugin("com.frugalmechanic" % "fm-sbt-s3-resolver" % "0.19.0")

addSbtPlugin("au.com.onegeek" %% "sbt-dotenv" % "2.1.146")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
