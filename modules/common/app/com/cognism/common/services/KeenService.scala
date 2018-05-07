package com.cognism.common.services

import javax.inject.{Inject, Singleton}

import com.cognism.common.utils.CommonConfig
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext

@Singleton
class KeenService @Inject()(ws:WSClient)(implicit ec:ExecutionContext) {
  private val log = Logger(classOf[KeenService])

  private def getSingleEventUrl(collection:String) = s"https://api.keen.io/3.0/projects/${CommonConfig.Keen.apiProject}/events/$collection"
  private val getMultipleEventUrl = s"https://api.keen.io/3.0/projects/${CommonConfig.Keen.apiProject}/events"

  def postEvent(collection:String, payload:JsValue):Unit = {
    if(CommonConfig.Keen.apiEnabled){
      ws.url(getSingleEventUrl(collection))
        .withQueryStringParameters(("api_key", CommonConfig.Keen.apiKey))
        .post(payload)
        .map{ response =>
          response.status match {
            case 200 | 201 | 202 | 204 =>
              log.info(s"Pushed event $payload to $collection. Response = ${response.body}")
            case _ =>
              log.error(s"Unable to push event. Reason: ${response.status} ${response.body}")
          }
        }
    }
  }

  def postEvents(collection:String, events:Seq[JsValue]):Unit = {
    if(CommonConfig.Keen.apiEnabled){
      ws.url(getMultipleEventUrl)
        .withQueryStringParameters(("api_key", CommonConfig.Keen.apiKey))
        .post(Json.obj(
          collection -> events
        ))
        .map{ response =>
          response.status match {
            case 200 | 201 | 202 | 204 =>
              log.info(s"Pushed ${events.size} events to $collection")
            case _ =>
              log.error(s"Unable to push events. Reason: ${response.status} ${response.body}")
          }
        }
    }
  }

}
