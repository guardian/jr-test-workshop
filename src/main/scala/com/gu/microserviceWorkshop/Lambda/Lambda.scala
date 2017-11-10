package com.gu.microserviceWorkshop
import io.circe.syntax._
import java.nio.charset.StandardCharsets.UTF_8
import java.io.{InputStream, OutputStream}

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.parser._
import cats.syntax.either._


case class APIRequest(body: String)
object APIRequest {
  implicit val stringAPIResponseDecoder: Decoder[APIResponse] = deriveDecoder
}

case class APIBody(number: String)
object APIBody {
  implicit val stringAPIBodyDecoder: Decoder[APIBody] = deriveDecoder
}


object Lambda {

  def isPrime(number: String) = {
    (2 until number.toInt) forall (x => number.toInt % x != 0)
  }

  def handler(in: InputStream, out: OutputStream): Unit = {
    val jsonPayload = scala.io.Source.fromInputStream(in).mkString("")

    val number = for{
      request <- decode[APIRequest](jsonPayload)
      body<- decode[APIBody](request.body)
    } yield body.number


    val resultJson = number match {
      case Right(x) => IsPrimeResult(number.toString, isPrime(x)).asJson.noSpaces
      case Left(e) => IsPrimeResult(e.toString, isPrime = false).asJson.noSpaces
    }

    val response = APIResponse(200,  Map("Content-Type" -> "application/json"), resultJson)

    //no spaces converts json to a string
    out.write(response.asJson.noSpaces.getBytes(UTF_8))

  }

}