logLevel := Level.Warn

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.3")

// iHeart Swagger Plugin
addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.7.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.9")

addSbtPlugin("com.frugalmechanic" % "fm-sbt-s3-resolver" % "0.16.0")

addSbtPlugin("au.com.onegeek" %% "sbt-dotenv" % "1.2.88")