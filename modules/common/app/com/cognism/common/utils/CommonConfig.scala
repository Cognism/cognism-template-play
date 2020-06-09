package com.cognism.common.utils

import com.typesafe.config.ConfigFactory

object CommonConfig {
  private val config = ConfigFactory.load("common.conf")

  object Api{
    val tokens = config.getStringList("common.api.tokens")
  }

}
