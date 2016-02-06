package utils

import play.api.mvc.{Result, Results}

import scala.util.control.NoStackTrace

case class ApplicationException(msg:String, response:Result) extends RuntimeException(msg) with NoStackTrace

object ApplicationException{
  def apply(msg:String):ApplicationException = {
    new ApplicationException(msg, Results.BadRequest(msg))
  }
}