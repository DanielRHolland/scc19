package uk.co.danrh.scc.bl

import uk.co.danrh.scc.dal.SharesDal
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, UserIdShareQuantities, UserShare}

trait SharesBl {
  def getShare(id: String): Share = SharesDal.getShare(id)
  def getShares(number: Int): List[Share] = SharesDal.getShares(number)
  def getSymbols(number: Int): List[String] = SharesDal.getShares(number).map(_.companySymbol)
  def createShare(share: Share): ResponseCode = SharesDal.insertOrUpdateShare(share)
  def getCurrencies: List[String] = CurrencyConverterConsumer.getCurrencyCodes()
  def getConversionRate(currency1:String,currency2:String): Double =
    CurrencyConverterConsumer.getConversionRate(currency1,currency2)
  def searchShares(number: Int, searchterms: Seq[String]): List[Share] =
    SharesDal.searchShares(number,searchterms)
  def getUserShares(userId: String) : List[UserShare] = SharesDal.getUserShares(userId)
  def getUserIdShareQuantities(userId: String) : UserIdShareQuantities = UserIdShareQuantities(userId, SharesDal.getShareQuantities(userId))
}
object SharesBl extends SharesBl