package utils

import java.security.MessageDigest
import play.api.cache.Cache
import play.api.i18n.Messages
import java.util.Date
import java.text.SimpleDateFormat
import java.util
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.Request
import play.api.{Play, Logger}
import scala.concurrent.duration._
import play.api.Play.current
import scala.language.postfixOps

import scala.reflect.ClassTag

/**
  * Container for misc helper functions.
  */
object Helper {
  private val dateFormatter = new SimpleDateFormat("dd.MM.yyyy")

  def md5(s: String): Option[String] = {
    if(s.isEmpty)
      None
    else
      Some(MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString)
  }

  def base64Encode(in: String): String ={
    if(in.nonEmpty)
      new sun.misc.BASE64Encoder().encode(in.getBytes())
    else ""
  }

  def base64Decode(in: String): String = {
    new String(new sun.misc.BASE64Decoder().decodeBuffer(in))
  }

  def formatDate(d: Date): String = {
    dateFormatter.format(d)
  }

  def formatDateFromSeconds(l: Long): String = {
    val d = new Date(l * 1000)
    formatDate(d)
  }

  def currentTimeInSeconds = System.currentTimeMillis() / 1000

  def time[A](name: String)(block: => A) = {
    val s = System.nanoTime
    val ret = block
    Logger.debug(s"TIMER $name - took "+(System.nanoTime-s)/1e6+"ms")
    ret
  }

  def getExtension(fileName:String):String= {
    val dotIndex = fileName.lastIndexOf(".")
    if(dotIndex>0){
      fileName.substring(dotIndex+1)
    }else{
      ""
    }
  }

}
