package utils

import com.mongodb.casbah.{MongoClient, MongoClientURI}
import models.Collections


object MongoManager {
  import utils.ApplicationConfig._
  //private val options = MongoClientOptions(connectTimeout = 30000) //, autoConnectRetry = true, maxAutoConnectRetryTime = 10000)

  lazy val DB = MongoClient(MongoClientURI(config.getString(s"application.mongo.uri"))).getDB(config.getString("application.mongo.db"))


}
