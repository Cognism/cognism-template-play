package com.cognism.common.utils

import com.typesafe.config.ConfigFactory

object CommonConfig {
  private val config = ConfigFactory.load("common.conf")

  object Keen{
    val apiEnabled = config.getBoolean("common.keen.api.enabled")
    val apiProject = config.getString("common.keen.api.project")
    val apiKey = config.getString("common.keen.api.key")
  }

  object Api{
    val tokens = config.getStringList("common.api.tokens")
  }

}
