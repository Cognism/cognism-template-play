package com.cognism.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._
import com.cognism.common.controllers.Secured

@Singleton
class Application @Inject() extends InjectedController with Secured {

  def status = Action { request =>
    val payload = Json.obj(
      "status" -> "OK"
    )

    Ok(payload)
  }

  def echo(msg:String) = ApiAction{ request =>
    Ok(msg)
  }

}