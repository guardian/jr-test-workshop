package com.gu.microserviceWorkshop.LambdaTest

import io.circe.syntax._
import java.nio.charset.StandardCharsets.UTF_8
import java.io.{InputStream, OutputStream}

import com.gu.microserviceWorkshop.{APIResponse, IsPrimeResult}

object Lambda extends App {

    val response = APIResponse(200,  Map("Content-Type" -> "application/json"), IsPrimeResult(49, isPrime = true).asJson.noSpaces)

    //no spaces converts json to a string
     print(response.asJson.noSpaces)

}