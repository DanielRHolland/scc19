package uk.co.danrh.scc.bl.ExternalApiWrapping.WorldTradingData


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import uk.co.danrh.scc.datatypes.{Share, SharePrice}

import scala.io.Source

trait WorldTradingDataConsumer {
  private val origin = "https://api.worldtradingdata.com/api/v1/stock"
  private val apiKey = "&api_token=QjczuFLvnZ7CY4yg7Sv9y1naLzUSCEIxyoOMZYkeZWEec9IC8BAlb245LOB0"


  def getFiveUpdatedShares(shares: List[Share]): WTResponse = {
    val symbols = "?symbol=" + createSymbolString(shares,4)
    println(symbols)
    val json = Source.fromURL(origin+symbols+apiKey)
   // val json = Source.fromFile("/home/dan/Sync/uni/scc19/test_scalatra/sctratest/src/main/scala/uk/co/danrh/scc/bl/ExternalApiWrapping/WorldTradingData/example.json")
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[WTResponse](json.reader())
  }

  private def createSymbolString(shares: List[Share], n:Int): String =
    if (shares.tail.nonEmpty && n!=0) postFixCode(shares.head.companySymbol) +"," + createSymbolString(shares.tail, n-1)
    else postFixCode(shares.head.companySymbol)

  private def postFixCode(code: String): String = if (code.last=='.') code + "L" else code +".L"

  def getAllUpdatedWTData(shares: List[Share]):List[WTDatum] =
    if (shares.isEmpty) Nil
    else getFiveUpdatedShares(shares.slice(0,5)).data ::: getAllUpdatedWTData(shares.slice(5,shares.length))

  def getAllUpdatedShares(shares: List[Share]): List[Share] = {
    def coRec(shares: List[Share], newData: List[WTDatum]): List[Share] = {
      if (shares.nonEmpty && newData.exists(_.symbol == postFixCode(shares.head.companySymbol))) {
        val newVal = newData.find(_.symbol == postFixCode(shares.head.companySymbol)).head
        shares.head.copy(sharePrice = SharePrice(currency = "GBP", newVal.price.toDouble),
          companyName = newVal.name) :: coRec(shares.tail, newData)
      } else if (shares.nonEmpty) coRec(shares.tail, newData)
        else Nil
    }
    val newData = getAllUpdatedWTData(shares)
   coRec(shares,newData)

  }

}

object WorldTradingDataConsumer extends WorldTradingDataConsumer