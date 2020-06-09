package com.cognism.utils

import com.typesafe.config.ConfigFactory
import play.api.i18n.Lang

/**
  * Main container for application configuration
  */
object ApplicationConfig {
  val config = ConfigFactory.load

}
