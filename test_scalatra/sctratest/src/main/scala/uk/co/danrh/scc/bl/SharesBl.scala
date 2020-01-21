package uk.co.danrh.scc.bl

import uk.co.danrh.scc.bl.ExternalApiWrapping.CurrencyConverter.CurrencyConverterConsumer
import uk.co.danrh.scc.dal.SharesDal
import uk.co.danrh.scc.datatypes.{Purchase, ResponseCode, SearchOptions, Share, UserIdShareQuantities, UserShare}

trait SharesBl {
  def getShare(id: String): Share = SharesDal.getShare(id)
  def getShares(searchOptions: SearchOptions): List[Share] = SharesDal.getShares(searchOptions)
  def getSymbols(number: Int): List[String] = SharesDal.getShares(SearchOptions(number)).map(_.companySymbol)
  def createShare(share: Share): ResponseCode = SharesDal.insertOrUpdateShare(share)
  def getCurrencies: List[String] = CurrencyConverterConsumer.getCurrencyCodes()
  def getConversionRate(currency1:String,currency2:String): Double =
    CurrencyConverterConsumer.getConversionRate(currency1,currency2)
  def getUserIdShareQuantities(userId: String) : UserIdShareQuantities = UserIdShareQuantities(userId, SharesDal.getShareQuantities(userId))
  def purchase(userId: String, purchase: Purchase) : ResponseCode = {
    var updated = true;
    if (!SharesDal.userShareExists(userId,purchase.companySymbol)) {
      SharesDal.insertOrUpdateUserShare(UserShare(userId, getShare(purchase.companySymbol), 0))
      updated = false
    }
    val userShare =  SharesDal.getUserShare(userId, purchase.companySymbol)
    val change = if (purchase.change > userShare.share.numberOfSharesAvailable) userShare.share.numberOfSharesAvailable
      else if (-purchase.change > userShare.quantity) -userShare.quantity
      else purchase.change
    val userShareUpdated = userShare.copy(
      share = userShare.share.copy(
        numberOfSharesAvailable =  userShare.share.numberOfSharesAvailable - change ),
        quantity = userShare.quantity + change)
    val responseCode  = SharesDal.insertOrUpdateUserShare(userShareUpdated)
    if (updated) ResponseCode.Updated(obj = userShareUpdated) else responseCode
  }
  def getUserIdShareQuantities(userId: String, searchOptions: SearchOptions) = UserIdShareQuantities(userId,
    SharesDal.getShareQuantities(userId, searchOptions))

  def deleteUserShare(userId: String, shareId: String): Any = SharesDal.deleteUserShare(userId, shareId)
}

object SharesBl extends SharesBl
