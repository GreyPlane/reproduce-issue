package com.myapp

import com.google.api.annotations.AnnotationsProto
import com.google.api.{AnnotationsProto => JProto}
import com.google.protobuf.Descriptors.{FileDescriptor, MethodDescriptor}
import com.myapp.shelf.grpc.ShelfOuterClass
import com.myapp.shelf.grpc.shelf.ShelfProto
import scala.jdk.CollectionConverters._

object Main {

  def getRulesByLens(methDesc: MethodDescriptor) = {
    AnnotationsProto.http.get(Options.convertMethodOptions(methDesc)) match {
      case Some(rule) =>
        rule +: rule.additionalBindings
      case None =>
        Seq.empty
    }
  }

  def getRulesByJavaAPI(methDesc: MethodDescriptor) = {
    val rule = methDesc.getOptions.getExtension(JProto.http)
    rule +: rule.getAdditionalBindingsList.asScala
  }

  def parseRules(fileDescriptor: FileDescriptor, from: String) = {
    println(s"result for $from")
    for {
      service <- fileDescriptor.getServices.asScala
      method <- service.getMethods.asScala
      sbindings = getRulesByLens(method)
      jbindings = getRulesByJavaAPI(method)
    } yield {
      println(s"for ${method.getName}, http rules from lens are ${sbindings}, http rules from java API are ${jbindings}")
    }
    println("---------")
  }

  def main(args: Array[String]): Unit = {
    val jFileDescriptor = ShelfOuterClass.getDescriptor
    val sFileDescriptor = ShelfProto.javaDescriptor

     parseRules(jFileDescriptor, "Java API")
     parseRules(sFileDescriptor, "ScalaPB")
  }
}
