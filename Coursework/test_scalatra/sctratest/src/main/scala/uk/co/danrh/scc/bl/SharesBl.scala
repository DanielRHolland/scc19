package uk.co.danrh.scc.bl

import uk.co.danrh.scc.dal.SharesDal
import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.Share

trait SharesBl {
  def getShare(id: String): Share = SharesDal.getShare(id)
  def getShares(number: Int): List[Share] = SharesDal.getShares(number)
  def createShare(share: Share): ResponseCode = SharesDal.createShare(share)
  def δ(a:Int,b:Int): Int =  if (a > b) a - b else b - a
}
object SharesBl extends SharesBl