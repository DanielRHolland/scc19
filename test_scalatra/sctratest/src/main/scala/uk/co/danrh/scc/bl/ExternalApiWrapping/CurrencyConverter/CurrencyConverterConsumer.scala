package uk.co.danrh.scc.bl.ExternalApiWrapping.CurrencyConverter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.io.Source

trait CurrencyConverterConsumer {
  private val origin = "http://localhost:8050/"
    def getCurrencyCodes(): List[String] = {
      val json = Source.fromURL(origin+"codes").mkString
      val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      mapper.readValue[List[String]](json)
    }

  def getConversionRate(currency1:String,currency2:String): Double =
      Source.fromURL(origin+"rate/"+currency1+"/"+currency2).mkString.toFloat
}

object CurrencyConverterConsumer extends CurrencyConverterConsumer
