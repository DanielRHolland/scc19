package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.{ResponseCode, Share, UserShare}

trait SharesDal {
  def getShare(id:String): Share
  def getShares(number: Int): List[Share]
  def insertOrUpdateShare(share: Share): ResponseCode
  def searchShares(number: Int, searchterms: Seq[String]): List[Share]
  def getUserShares(userId: String): List[UserShare]
}

object SharesDal extends SharesDalSqlite