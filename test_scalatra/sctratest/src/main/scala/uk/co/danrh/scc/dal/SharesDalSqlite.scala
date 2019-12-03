package uk.co.danrh.scc.dal

import java.time.Instant

import Tables._
import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share}

import scala.collection.immutable.Nil
import scala.slick.driver.SQLiteDriver
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.jdbc.PositionedResultIterator

trait SharesDalSqlite extends SharesDal {

  def convRowsToSharesRec(num:Int, itr: PositionedResultIterator[SharesRow]): List[Share] =
    if (num>0 && itr.hasNext) convertRowToShare(itr.next())::convRowsToSharesRec(num-1, itr) else Nil

  override def getShares(number: Int): List[Share] =
    db.withDynSession(shares.results(number).map(r => convRowsToSharesRec(number,r)).right.get)

  override def getShare(id: String): Share = convertRowToShare(db.withDynSession(
    shares.filter(_.companySymbol===id).first))

  val db: SQLiteDriver.backend.DatabaseDef = Database.forURL(
      "jdbc:sqlite:shares.db",
      driver = "org.sqlite.JDBC")

  override def insertOrUpdateShare(share: Share): ResponseCode = {
    db withDynSession {
      shares.insertOrUpdate(
        share.companySymbol,
        share.companyName,
        share.numberOfSharesAvailable,
        Instant.now.getEpochSecond,
        share.sharePrice.currency,
        share.sharePrice.value)
    }
  ResponseCode.Created
  }


  override def searchShares(number: Int, searchterms: Seq[String]): List[Share] = {
    db.withDynSession(shares
    .filter( _.companyName like "%"+searchterms.head+"%")
      .results(number).map(r => convRowsToSharesRec(number,r)).right.get)
  }
}

