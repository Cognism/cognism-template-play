package controllers

import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller {

  def swagger = Action { request =>
    Ok(views.html.swagger())
  }

  def status = Action { request =>
    val payload = Json.obj(
      "status" -> "OK"
    )

    Ok(payload)
  }

}