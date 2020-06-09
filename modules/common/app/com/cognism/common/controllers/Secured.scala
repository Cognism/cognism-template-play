package com.cognism.common.controllers

import com.cognism.common.utils.CommonConfig
import play.api.mvc._

import scala.concurrent.Future

trait Secured {
  this: InjectedController =>

  val HEADER_API_TOKEN = "Authorization"
  val QUERY_API_TOKEN = "api_key"

  private def apiKey[T](request: Request[T]): Option[String] = {
    val headerToken = request.headers.get(HEADER_API_TOKEN)
    if (headerToken.isEmpty) {
      request.queryString.get(QUERY_API_TOKEN).map(_.mkString(""))
    } else {
      headerToken
    }
  }

  def ApiAction(f: => Request[AnyContent] => Result) = Action { request =>
    apiKey(request) match {
      case Some(token) if CommonConfig.Api.tokens.contains(token) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

  def ApiActionAsync[A](bodyParser: BodyParser[A])(f: => Request[A] => Future[Result]) = Action.async(bodyParser) { request =>
    apiKey(request) match {
      case Some(token) if CommonConfig.Api.tokens.contains(token) =>
        f(request)
      case _ =>
        Future.successful(Results.Unauthorized)
    }
  }

  def ApiAction[A](bodyParser: BodyParser[A])(f: => Request[A] => Result) = Action(bodyParser) { request =>
    apiKey(request) match {
      case Some(token) if CommonConfig.Api.tokens.contains(token) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

}
