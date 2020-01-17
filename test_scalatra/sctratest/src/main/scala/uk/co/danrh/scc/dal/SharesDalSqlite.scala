package uk.co.danrh.scc.dal

import java.time.Instant

import Tables._
import uk.co.danrh.scc.datatypes.{ResponseCode, Share, SharePrice, ShareQuantity, UserIdShareQuantities, UserShare}

import scala.collection.immutable.Nil
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.jdbc.PositionedResultIterator

trait SharesDalSqlite extends SharesDal {

  def convRowsToSharesRec(num:Int, itr: PositionedResultIterator[SharesRow]): List[Share] =
    if (num>0 && itr.hasNext) convertRowToShare(itr.next())::convRowsToSharesRec(num-1, itr) else Nil

  override def getShares(number: Int): List[Share] =
    db withDynSession shares.results(number).map(r => convRowsToSharesRec(number,r)).right.get

  override def getShare(id: String): Share = convertRowToShare(db withDynSession
    shares.filter(_.companySymbol===id).first)

  override def userShareExists(userId: String, companySymbol: String): Boolean =
    db withDynSession userShares.filter(_.userId === userId).filter(_.companySymbol === companySymbol).exists.run

  override def insertOrUpdateShare(share: Share): ResponseCode = {
    db withDynSession insOrUpdShare(share)
    ResponseCode.Created(obj = share)
  }

  private def insOrUpdShare(share: Share) =
    shares.insertOrUpdate(
      share.companySymbol,
      share.companyName,
      share.numberOfSharesAvailable,
      Instant.now.getEpochSecond,
      share.sharePrice.currency,
      share.sharePrice.value)

  override def searchShares(number: Int, searchterms: Seq[String]): List[Share] =
    db withDynSession shares.filter( _.companyName like "%"+searchterms.head+"%").results(number)
      .map(r => convRowsToSharesRec(number,r)).right.get

  override def getUserShares(userId: String, num: Int = 10): List[UserShare] = {
    type ResultRow = (String,String,String,Int,Long,String,Double)
    def convRow(row: ResultRow): UserShare =
      UserShare(row._1, Share(SharePrice(row._6,row._7), row._3,row._2,row._4,row._5), row._4)
    def convRowsRec(num:Int, itr: PositionedResultIterator[ResultRow]): List[UserShare] =
      if (num>0 && itr.hasNext) convRow(itr.next())::convRowsRec(num-1, itr) else Nil

    db withDynSession {
        val res = for {
          (usshs, shs) <- userShares.filter(_.userId === userId) join shares on(_.companySymbol === _.companySymbol)
        } yield (usshs.userId, usshs.companySymbol, shs.companyName, usshs.quantity,
        shs.lastUpdate, shs.currency, shs.value)
        res.results(num).map(r => convRowsRec(r.length, r)).right.get
    }
  }

  override def getShareQuantities(userId: String): List[ShareQuantity] = {
    type ResultRow = (String, String, String, Int, Long, String, Double, Int)
    def convRow(row: ResultRow): ShareQuantity =
      ShareQuantity(Share(SharePrice(row._6, row._7), row._3, row._2, row._8, row._5), row._4)
    def convRowsRec(num: Int, itr: PositionedResultIterator[ResultRow]): List[ShareQuantity] =
      if (num > 0 && itr.hasNext) convRow(itr.next()) :: convRowsRec(num - 1, itr) else Nil

    db withDynSession {
      val res = for {
        (usshs, shs) <- userShares.filter(_.userId === userId) join shares on (_.companySymbol === _.companySymbol)
      } yield (usshs.userId, usshs.companySymbol, shs.companyName, usshs.quantity,
        shs.lastUpdate, shs.currency, shs.value, shs.sharesAvailable)
      res.results(10).map(r => convRowsRec(10, r)).right.get
    }
  }

  override def getUserShare(userId: String, companySymbol: String): UserShare = {
    db withDynSession {
      val res = for {
        (usshs, shs) <- userShares.filter(_.userId === userId).filter(_.companySymbol === companySymbol) join shares on (_.companySymbol === _.companySymbol)
      } yield (usshs.userId, usshs.companySymbol, shs.companyName, usshs.quantity,
        shs.lastUpdate, shs.currency, shs.value, shs.sharesAvailable)
      val row = res.first
      UserShare(row._1,Share(SharePrice(row._6,row._7),row._3,row._2,row._8,row._5),row._4)
    }
  }

  override def insertOrUpdateUserShare(userShare: UserShare): ResponseCode = {
    val share = userShare.share
    db withDynSession {
      insOrUpdShare(share)
      userShares.insertOrUpdate(
        userShare.userId,
        share.companySymbol,
        userShare.quantity
      )
    }
    ResponseCode.Created(obj = userShare)
  }
}

