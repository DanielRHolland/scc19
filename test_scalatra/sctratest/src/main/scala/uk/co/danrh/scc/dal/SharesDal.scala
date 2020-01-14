package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.{ResponseCode, Share, ShareQuantity, UserShare}

trait SharesDal {
  def getShare(id: String): Share

  def getShares(number: Int): List[Share]

  def insertOrUpdateShare(share: Share): ResponseCode

  def searchShares(number: Int, searchterms: Seq[String]): List[Share]

  def getUserShares(userId: String, num: Int): List[UserShare]

  def getShareQuantities(userId: String): List[ShareQuantity]

  def getUserShare(userId: String, companySymbol: String) : UserShare

  def insertOrUpdateUserShare(userShare: UserShare): ResponseCode

  def userShareExists(userId: String, companySymbol: String) : Boolean
}


object SharesDal extends SharesDalSqlite


