package utils

import com.typesafe.config.ConfigFactory
import play.api.i18n.Lang

/**
 * Main container for application configuration
 */
object ApplicationConfig {
  val config = ConfigFactory.load

  object Api{
    val tokens = config.getStringList("application.api.tokens")
  }

  object Swagger{
    val path = config.getString("swagger.api.basepath")
  }
}
