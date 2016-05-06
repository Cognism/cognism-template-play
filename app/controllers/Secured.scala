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

  val HEADER_API_TOKEN = "X-API-Token"
  val QUERY_API_TOKEN = "token"

  private def tokenValue[T](request:Request[T]):Option[String] = {
    val headerToken = request.headers.get(HEADER_API_TOKEN)
    if(headerToken.isEmpty){
      request.queryString.get(QUERY_API_TOKEN).map(_.mkString(""))
    }else{
      headerToken
    }
  }

  def ApiAction(f: => Request[AnyContent] => Result) = Action{ request =>
    tokenValue(request) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

  def ApiActionAsync[A](bodyParser:BodyParser[A])(f: => Request[A] => Future[Result]) = Action.async(bodyParser){ request =>
    tokenValue(request) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Future.successful(Results.Unauthorized)
    }
  }

  def ApiAction[A](bodyParser:BodyParser[A])(f: => Request[A] => Result) = Action(bodyParser){ request =>
    tokenValue(request) match {
      case Some(token) if(ApplicationConfig.Api.tokens.contains(token)) =>
        f(request)
      case _ =>
        Results.Unauthorized
    }
  }

}
