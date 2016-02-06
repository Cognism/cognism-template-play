import play.api._

import play.api.mvc._
import play.api.mvc.RequestHeader
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter
import utils.{ApplicationException, ApplicationConfig}
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info(s"[GLOBAL] Starting application")
  }

  override def onStop(app: Application) {
    Logger.info(s"[GLOBAL] Stopping application")
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
    ex.getCause match {
      case ApplicationException(msg,response) =>
        Future.successful(response)
      case _ =>
        super.onError(request,ex)
    }
  }

  override def doFilter(action: EssentialAction) = {
    Filters(
      LoggingFilter(action),
      CORSFilter(),
      new GzipFilter(shouldGzip = (request, response) => request.headers.get("Accept").exists(_.contains("gzip")) || request.headers.get("Accept-Encoding").exists(_.contains("gzip")) )
    )
  }

}

object LoggingFilter extends Filter {

  def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      Logger.info(s"${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
      result.withHeaders("Request-Time" -> (requestTime.toString + "ms"))
    }
  }
}
