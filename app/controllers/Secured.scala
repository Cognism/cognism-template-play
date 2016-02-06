package controllers

import play.api.mvc._
import play.api.mvc.Controller
import utils.ApplicationConfig

import scala.concurrent.Future


/**
  * Created by stipe on 24.12.2015..
  */
trait Secured {
  this: Controller =>

  val API_TOKEN = "X-API-Token"

  def ApiAction(f: => Request[AnyContent] => Result) = Action{ request =>
    request.headers.get(API_TOKEN) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

  def ApiActionAsync[A](bodyParser:BodyParser[A])(f: => Request[A] => Future[Result]) = Action.async(bodyParser){ request =>
    request.headers.get(API_TOKEN) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Future.successful(Results.Unauthorized)
    }
  }

  def ApiAction[A](bodyParser:BodyParser[A])(f: => Request[A] => Result) = Action(bodyParser){ request =>
    request.headers.get(API_TOKEN) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

}
