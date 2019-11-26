package uk.co.danrh.scc.dal

import java.text.SimpleDateFormat
import java.util.Date

import Tables._
import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, SharePrice}

import scala.concurrent.Future
import scala.slick.driver.SQLiteDriver
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.jdbc.StaticQuery
import scala.slick.jdbc.meta.MTable

trait SharesDalSqlite extends  SharesDal {

  val sharePrice = SharePrice("GBP",1000)
  val sdf = new SimpleDateFormat("dd/MM/yyyy")
  val d: Date = sdf.parse("21/12/2012")
  var sharesl = List(
    Share(sharePrice,"Company 1", "CY1",1000,d),
    Share(sharePrice,"Company 2", "CY2",890,d),
    Share(sharePrice,"Company 3", "CY3",300,d),
    Share(sharePrice,"Company 4", "CY4",200,d),
    Share(sharePrice,"Company 5", "CY5",700,d),
    Share(sharePrice,"Company 6", "CY6",650,d))

  override def getShares(number: Int): List[Share] =
    if (number== -1 || number>=sharesl.length) sharesl
    else sharesl.slice(0, number)

  //Bull above////////////////////////////////////////////////////////////

  override def getShare(id: String): Share =
    {
//      db withDynSession {
//        val sym: String =
//          shares.filter(_.companySymbol == id).map(_.companySymbol).results(1).toSeq.
//
//      Share(sharePrice,"Company 1", sym,1000,d)
//    }
      //db.run(shares.map(_.companySymbol).result)
      val fut: String = db.withDynSession(
  shares.filter(_.companySymbol===id).map(_.companySymbol).results(0).toSeq.head.next())
      Share(sharePrice,"Company 1 id:"+fut, fut,1000,d)
    }
//  override def getShare(id:String): Share = //getShares(-1).find(s => s.companySymbol == id).get
//    db withDynSession {
//      for ((s <- shares if s.companySymbol == id)
//        yield s).result.headoption
//        .resShare(
//          SharePrice(s.currency.toString(), s.value(0)),
//          s.companySymbol, s.companyName, s.)
//    }
//  db.run((for (user <- users if user.id === id) yield user).result.headOption)



  val db: SQLiteDriver.backend.DatabaseDef = Database.forURL(
      "jdbc:sqlite:shares.db",
      driver = "org.sqlite.JDBC")
//  def getShare(id: String): Share
//  def getShares(number: Int): List[Share]
  override def createShare(share: Share): ResponseCode = {
  db withDynSession {
    shares.insert(
      share.companySymbol,
      share.companyName,
      share.numberOfSharesAvailable,
      share.lastUpdate.toString,
      share.sharePrice.currency,
      share.sharePrice.value)
  }
  ResponseCode.Created
  }
}

