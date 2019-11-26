package uk.co.danrh.scc.dal

import java.text.SimpleDateFormat
import java.util.Date

import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, SharePrice}


trait SharesDalMock extends SharesDal {
  val sharePrice = SharePrice("GBP",1000)
  val sdf = new SimpleDateFormat("dd/MM/yyyy")
  val d: Date = sdf.parse("21/12/2012")

  //  var A:Map[Char,Int] = Map()


  var shares = List(
    Share(sharePrice,"Company 1", "CY1",1000,d),
    Share(sharePrice,"Company 2", "CY2",890,d),
    Share(sharePrice,"Company 3", "CY3",300,d),
    Share(sharePrice,"Company 4", "CY4",200,d),
    Share(sharePrice,"Company 5", "CY5",700,d),
    Share(sharePrice,"Company 6", "CY6",650,d))

  override def getShares(number: Int): List[Share] =
    if (number== -1 || number>=shares.length) shares
    else shares.slice(0, number)
  override def getShare(id:String): Share = getShares(-1).find(s => s.companySymbol == id).get
  override def createShare(share: Share): ResponseCode = {
    shares = share :: shares
    ResponseCode.Created
  }
}