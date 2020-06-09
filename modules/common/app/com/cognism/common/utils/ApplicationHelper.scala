package com.cognism.common.utils

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date

import org.joda.time.DateTime
import play.api.libs.json.{JsObject, JsValue}

import scala.language.postfixOps
import scala.util.Random

/**
  * Container for misc helper functions.
  */
object ApplicationHelper {
  private val dateFormatter = new SimpleDateFormat("dd.MM.yyyy")
  val alphabet = ('a' to 'z').toList

  def isValidMD5(s: String): Boolean = s.matches("[a-fA-F0-9]{32}")

  def isSameDayMonthYear(time1: Long, time2: Long): Boolean = {
    val date1 = new DateTime(time1)
    val date2 = new DateTime(time2)

    date1.year().get() == date2.year().get() && date1
      .monthOfYear()
      .get() == date2.monthOfYear().get() && date1.dayOfMonth().get() == date2
      .dayOfMonth()
      .get()
  }

  def md5(s: String): String = {
    if (s.isEmpty)
      ""
    else
      MessageDigest
        .getInstance("MD5")
        .digest(s.getBytes)
        .map("%02x".format(_))
        .mkString
  }

  def sha256(s: String): String = {
    if (s.isEmpty)
      ""
    else
      MessageDigest
        .getInstance("SHA-256")
        .digest(s.getBytes)
        .map("%02x".format(_))
        .mkString
  }

  def base64Encode(in: String): String = {
    if (in.nonEmpty)
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

  def getExtension(fileName: String): String = {
    val dotIndex = fileName.lastIndexOf("")
    if (dotIndex > 0) {
      fileName.substring(dotIndex + 1)
    } else {
      ""
    }
  }

  def shortRandomId: String = {
    Random.alphanumeric.take(12).toList.mkString
  }

  case class Timer(name: String) {
    private val startTime = System.nanoTime()
    private val timeblocks =
      scala.collection.mutable.Map.empty[String, (Int, Int)]

    def time[A](name: String)(block: => A) = {
      val s = System.nanoTime
      val ret = block
      timeblocks.get(name) match {
        case Some((duration, counter)) =>
          timeblocks.put(
            name,
            (duration + ((System.nanoTime - s) / 1e6).toInt, counter + 1)
          )
        case _ =>
          timeblocks.put(name, (((System.nanoTime - s) / 1e6).toInt, 1))
      }
      ret
    }

    override def toString(): String = {
      val totalExecutionTime = ((System.nanoTime - startTime) / 1e6).toInt
      val buffer = new StringBuilder(
        s"[TIMER] $name - took ${totalExecutionTime}ms => "
      )

      timeblocks.foreach {
        case (blockname, (totaltime, counter)) =>
          buffer.append(
            s"$blockname=${totaltime}ms[avg=${(totaltime / counter)}ms] "
          )
      }

      buffer.toString()
    }
  }

  object JSON {
    import play.api.libs.json.Reads

    import scala.reflect.runtime.universe._

    def find[T: Reads](name: String)(implicit json: JsValue, tag: TypeTag[T]): T = {
      val nameTree = name.split("\\.")
      val leaf = nameTree.last
      val branches = nameTree.init

      var obj = json
      val processedBranches = scala.collection.mutable.ListBuffer[String]()
      branches.foreach { branchName =>
        processedBranches += branchName
        (obj \ branchName).asOpt[JsObject] match {
          case Some(branchObj) =>
            obj = branchObj
          case _ =>
            throw ApplicationException(
              s"Bad JSON: { ${processedBranches.mkString("")}:Object }"
            )
        }
      }

      as[T](leaf, obj, Some(name))
    }

    def findOption[T: Reads](name: String, strict: Boolean = false)(
        implicit json: JsValue
    ): Option[T] = {
      val nameTree = name.split("\\.")
      val leaf = nameTree.last
      val branches = nameTree.init

      var obj = json
      val processedBranches = scala.collection.mutable.ListBuffer[String]()
      branches.foreach { branchName =>
        processedBranches += branchName
        (obj \ branchName).asOpt[JsObject] match {
          case Some(branchObj) =>
            obj = branchObj
          case _ =>
            if (strict)
              throw ApplicationException(
                s"Bad JSON: { ${processedBranches.mkString("")}:Object }"
              )
        }
      }

      asOption[T](leaf, obj)
    }

    def contains[T: Reads](name: String, value: T)(implicit json: JsValue): Boolean = {
      val nameTree = name.split("\\.")

      var objects = Seq(json)
      nameTree.foreach { e => objects = objects.flatMap(o => o \\ e) }

      objects.find(o => o.asOpt[T] == Some(value)).isDefined
    }

    def as[T: Reads](name: String, json: JsValue, path: Option[String] = None)(
        implicit tag: TypeTag[T]
    ): T = {
      (json \ name).asOpt[T] match {
        case Some(value) =>
          value.asInstanceOf[T]
        case _ =>
          throw ApplicationException(
            s"Bad JSON: { ${path.getOrElse(name)}:${typeOf[T]} }"
          )
      }
    }

    def asOption[T: Reads](name: String, json: JsValue): Option[T] = {
      (json \ name).asOpt[T]
    }

  }

}
