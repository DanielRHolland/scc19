package uk.co.danrh.scc.dal

import java.time.Instant

import Tables._
import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, SharePrice}

import scala.collection.immutable.Nil
import scala.slick.driver.SQLiteDriver
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.jdbc.PositionedResultIterator

trait SharesDalSqlite extends SharesDal {



  override def getShares(number: Int): List[Share] = {
    def rowsRec(num:Int, itr: PositionedResultIterator[SharesRow]): List[Share] =
      if (num>0 && itr.hasNext) convertRowToShare(itr.next())::rowsRec(num-1, itr) else Nil
    db.withDynSession(shares.results(number).map(r => rowsRec(number,r)).right.get)
  }


  override def getShare(id: String): Share = convertRowToShare(db.withDynSession(
    shares.filter(_.companySymbol===id).first))

  private def convertRowToShare(row:SharesRow) =
    Share(SharePrice(row._5,row._6), row._2, row._1, row._3, row._4)

  val db: SQLiteDriver.backend.DatabaseDef = Database.forURL(
      "jdbc:sqlite:shares.db",
      driver = "org.sqlite.JDBC")
  override def createShare(share: Share): ResponseCode = {
    db withDynSession {
      shares.insert(
        share.companySymbol,
        share.companyName,
        share.numberOfSharesAvailable,
        Instant.now.getEpochSecond,
        share.sharePrice.currency,
        share.sharePrice.value)
    }
    ResponseCode.Created
  }
}

