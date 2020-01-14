package uk.co.danrh.scc.bl

import uk.co.danrh.scc.dal.SharesDal
import uk.co.danrh.scc.datatypes.{Purchase, ResponseCode, Share, UserIdCompanySymbol, UserIdShareQuantities, UserShare}

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
  def purchase(purchase: Purchase) : ResponseCode = {
    val userShare = SharesDal.getUserShare(purchase.userId, purchase.companySymbol)
    val change = if (purchase.change > userShare.share.numberOfSharesAvailable) userShare.share.numberOfSharesAvailable
      else if (-purchase.change > userShare.quantity) -userShare.quantity
      else purchase.change
    val userShareUpdated = userShare.copy(
      share = userShare.share.copy(
        numberOfSharesAvailable =  userShare.share.numberOfSharesAvailable - change ),
        quantity = userShare.quantity + change)
    SharesDal.insertOrUpdateUserShare(userShareUpdated)
  }
  def createUserShare(userIdCompanySymbol: UserIdCompanySymbol): ResponseCode = {
    if (!SharesDal.userShareExists(userIdCompanySymbol.userId,userIdCompanySymbol.companySymbol)) {
      SharesDal.insertOrUpdateUserShare(UserShare(userIdCompanySymbol.userId, getShare(userIdCompanySymbol.companySymbol), 0))
    } else ResponseCode.Failed("Failed: UserShare already exists")
  }
}

object SharesBl extends SharesBl