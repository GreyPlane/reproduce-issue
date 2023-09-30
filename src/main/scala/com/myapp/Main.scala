package com.myapp

import com.google.api.annotations.AnnotationsProto
import com.google.protobuf.Descriptors.{FileDescriptor, MethodDescriptor}
import com.myapp.shelf.grpc.ShelfOuterClass
import com.myapp.shelf.grpc.shelf.ShelfProto
import scala.jdk.CollectionConverters._

object Main {

  def getRules(methDesc: MethodDescriptor) = {
    AnnotationsProto.http.get(Options.convertMethodOptions(methDesc)) match {
      case Some(rule) =>
        rule +: rule.additionalBindings
      case None =>
        Seq.empty
    }
  }

  def parseRules(fileDescriptor: FileDescriptor) = {
    for {
      service <- fileDescriptor.getServices.asScala
      method <- service.getMethods.asScala
      rules = getRules(method)
      binding <- rules
    } yield {
      method -> binding
    }
  }

  def main(args: Array[String]): Unit = {
    val jFileDescriptor = ShelfOuterClass.getDescriptor
    val sFileDescriptor = ShelfProto.javaDescriptor

    val jResult = parseRules(jFileDescriptor)
    val sResult = parseRules(sFileDescriptor)
    // these two should be same
    println(jResult)
    println(sResult)
  }
}
