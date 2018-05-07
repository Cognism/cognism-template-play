import com.cognism.common.utils.ApplicationException
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._
import javax.inject.Singleton

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(Status(statusCode)(s"HTTP [$statusCode] Client Error {$message}"))
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    exception.getCause match {
      case ApplicationException(msg,response) =>
        Future.successful(response)
      case _ =>
        Future.successful(InternalServerError("A server error occurred: " + exception.getMessage))
    }
  }
}