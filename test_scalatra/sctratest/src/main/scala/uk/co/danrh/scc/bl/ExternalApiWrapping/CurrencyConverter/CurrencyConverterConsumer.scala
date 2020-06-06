package uk.co.danrh.scc.bl.ExternalApiWrapping.CurrencyConverter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.grpc.ManagedChannelBuilder
import uk.co.danrh.protos.curr.curr.{CurrencyGrpc, SearchOptions, ConversionSymbols}
import uk.co.danrh.protos.curr.curr.CurrencyGrpc.CurrencyBlockingStub

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

trait CurrencyConverterGrpcConsumer extends CurrencyConverterConsumer {
  val channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051).usePlaintext()
    .asInstanceOf[ManagedChannelBuilder[_]].build
  val CurrencyServer = CurrencyGrpc.blockingStub(channel)



  override def getCurrencyCodes(): List[String] = CurrencyServer.getSymbols(SearchOptions(number = 13)).symbol.toList

  override def getConversionRate(currency1: String, currency2: String): Double = CurrencyServer.getRate(ConversionSymbols(currencyFrom = currency1, currencyTo = currency2)).rate
  //override def getCurrencyCodes(): List[String] = List("AED", "GBP")
}

object CurrencyConverterConsumer extends CurrencyConverterGrpcConsumer