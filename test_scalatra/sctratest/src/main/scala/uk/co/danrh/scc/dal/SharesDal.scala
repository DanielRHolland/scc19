package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, SharePrice}

trait SharesDal {
  def getShare(id:String): Share
  def getShares(number: Int): List[Share]
  def insertOrUpdateShare(share: Share): ResponseCode
}

object SharesDal extends SharesDalSqlite