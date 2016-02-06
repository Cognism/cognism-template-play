package utils

import play.api.libs.json._

object PlayJson {
  import scala.reflect.runtime.universe._
  import play.api.libs.json.Reads

  def find[T:Reads](name: String)(implicit json:JsValue, tag: TypeTag[T]):T = {
    val nameTree = name.split("\\.")
    val leaf = nameTree.last
    val branches = nameTree.init

    var obj = json
    val processedBranches = scala.collection.mutable.MutableList[String]()
    branches.foreach{branchName =>
      processedBranches += branchName
      (obj \ branchName).asOpt[JsObject] match {
        case Some(branchObj) =>
          obj = branchObj
        case _ =>
          throw ApplicationException(s"Bad JSON: { ${processedBranches.mkString(".")}:Object }")
      }
    }

    as[T](leaf, obj, Some(name))
  }

  def findOption[T:Reads](name: String, strict:Boolean=false)(implicit json:JsValue):Option[T] = {
    val nameTree = name.split("\\.")
    val leaf = nameTree.last
    val branches = nameTree.init

    var obj = json
    val processedBranches = scala.collection.mutable.MutableList[String]()
    branches.foreach{branchName =>
      processedBranches += branchName
      (obj \ branchName).asOpt[JsObject] match {
        case Some(branchObj) =>
          obj = branchObj
        case _ =>
          if(strict) throw ApplicationException(s"Bad JSON: { ${processedBranches.mkString(".")}:Object }")
      }
    }

    asOption[T](leaf, obj)
  }

  def contains[T:Reads](name: String, value:T)(implicit json:JsValue):Boolean = {
    val nameTree = name.split("\\.")

    var objects = Seq(json)
    nameTree.foreach{e =>
      objects = objects.flatMap(o => o \\ e)
    }

    objects.find(o => o.asOpt[T] == Some(value)).isDefined
  }

  def as[T:Reads](name:String, json:JsValue, path:Option[String]=None)(implicit tag:TypeTag[T]):T = {
    (json \ name).asOpt[T] match {
      case Some(value) =>
        value.asInstanceOf[T]
      case _ =>
        throw ApplicationException(s"Bad JSON: { ${path.getOrElse(name)}:${typeOf[T]} }")
    }
  }

  def asOption[T:Reads](name:String, json:JsValue):Option[T] = {
    (json \ name).asOpt[T]
  }



}