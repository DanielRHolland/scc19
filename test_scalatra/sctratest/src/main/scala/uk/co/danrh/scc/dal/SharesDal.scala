package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.Share

trait SharesDal {
  def getShare(id:String): Share
  def getShares(number: Int): List[Share]
  def insertOrUpdateShare(share: Share): ResponseCode
  def searchShares(number: Int, searchterms: Seq[String]): List[Share]
}

object SharesDal extends SharesDalSqlite