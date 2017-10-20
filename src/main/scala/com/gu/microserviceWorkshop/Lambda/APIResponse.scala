package com.gu.microserviceWorkshop

import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.extras.semiauto._


case class APIResponse(statusCode: Int, headers: Map[String, String], body: String)


object APIResponse {

  private val stringAPIResponseEncoder : Encoder[APIResponse] = deriveEncoder

  //needed as API gateway requires that the body is returned as a string
  implicit def responseEncoder: Encoder[APIResponse] = Encoder.instance { responseA =>
    responseA.copy(body = responseA.body.asJson.noSpaces).asJson(stringAPIResponseEncoder)
  }

  def fromResult(body: String): APIResponse = {
      APIResponse(200,  Map("Content-Type" -> "application/json"), body)
  }

}