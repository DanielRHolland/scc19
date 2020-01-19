package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.{ResponseCode, SearchOptions, Share, ShareQuantity, UserShare}

trait SharesDal {
  def getShare(id: String): Share

  def getShares(searchOptions: SearchOptions): List[Share]

  def insertOrUpdateShare(share: Share): ResponseCode

  def getShareQuantities(userId: String, searchOptions: SearchOptions): List[ShareQuantity]

  def getUserShare(userId: String, companySymbol: String) : UserShare

  def insertOrUpdateUserShare(userShare: UserShare): ResponseCode

  def userShareExists(userId: String, companySymbol: String) : Boolean
}


object SharesDal extends SharesDalSqlite


