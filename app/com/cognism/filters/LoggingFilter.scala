package com.cognism.filters

import javax.inject.Inject

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class LoggingFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {

  private val log = Logger("LoggingFilter")

  private def apiKey(request: RequestHeader): Option[String] = {
    request.queryString.get("api_key").map(_.mkString("")) match {
      case session @ Some(_) =>
        session
      case _ =>
        request.headers.get("Authorization")
    }
  }

  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime

      apiKey(requestHeader) match {
        case Some(key) =>
          log.info(
            s"${requestHeader.method} ${requestHeader.uri.replace(key, key.map(_ => '*').mkString)} took ${requestTime}ms and returned ${result.header.status}"
          )
        case _ =>
          log.info(s"[anonymous] ${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
      }

      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}
