package uk.co.danrh.scc.bl

import uk.co.danrh.scc.dal.SharesDal
import uk.co.danrh.scc.datatypes.{ResponseCode, Share}

trait SharesBl {
  def getShare(id: String): Share = SharesDal.getShare(id)
  def getShares(number: Int): List[Share] = SharesDal.getShares(number)
  def createShare(share: Share): ResponseCode = SharesDal.insertOrUpdateShare(share)
  def getCurrencies(): List[String] = CurrencyConverterConsumer.getCurrencyCodes()
  def getConversionRate(currency1:String,currency2:String): Double =
    CurrencyConverterConsumer.getConversionRate(currency1,currency2)
  def searchShares(number: Int, searchterms: Seq[String]): List[Share] =
    SharesDal.searchShares(number,searchterms)
}
object SharesBl extends SharesBl