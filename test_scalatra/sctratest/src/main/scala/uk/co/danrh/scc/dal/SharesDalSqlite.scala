package uk.co.danrh.scc.dal

import java.time.Instant

import Tables._
import uk.co.danrh.scc.datatypes.{ResponseCode, SearchOptions, Share, SharePrice, ShareQuantity, UserShare}

import scala.collection.immutable.Nil
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import scala.slick.jdbc.PositionedResultIterator

trait SharesDalSqlite extends SharesDal {

  def convRowsToSharesRec(itr: PositionedResultIterator[SharesRow]): List[Share] =
    if (itr.hasNext) convertRowToShare(itr.next())::convRowsToSharesRec(itr) else Nil

  override def getShares(searchOptions: SearchOptions = SearchOptions()): List[Share] = {
    var sortedShares = shares.sortBy(OptionsUtil.getSortSharesBy(searchOptions))
    if (searchOptions.terms != null && searchOptions.terms.nonEmpty) sortedShares = shares.filter(OptionsUtil.getSearchSharesBy(searchOptions))
    db withDynSession sortedShares.results(searchOptions.numberOfResults).map(convRowsToSharesRec(_)).right.get
  }

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

  override def getShareQuantities(userId: String, searchOptions: SearchOptions = SearchOptions()): List[ShareQuantity] = {
    type ResultRow = (String, String, String, Int, Long, String, Double, Int)
    def convRow(row: ResultRow): ShareQuantity =
      ShareQuantity(Share(SharePrice(row._6, row._7), row._3, row._2, row._8, row._5), row._4)
    def convRowsRec(itr: PositionedResultIterator[ResultRow]): List[ShareQuantity] =
      if (itr.hasNext) convRow(itr.next()) :: convRowsRec(itr) else Nil
    val sortUserShares = OptionsUtil.isSortUserShares(searchOptions)
    val filteredUserShares = userShares.filter(_.userId===userId)
    val sortedUserShares = if (sortUserShares) filteredUserShares.sortBy(OptionsUtil.getSortUserSharesBy(searchOptions)) else filteredUserShares
    val sortedShares = if (!sortUserShares) shares.sortBy(OptionsUtil.getSortSharesBy(searchOptions)) else shares
    db withDynSession {
      val res = for {
        (usshs, shs) <- sortedUserShares join sortedShares on (_.companySymbol === _.companySymbol) filter(OptionsUtil.getSearchSharesUserSharesBy(searchOptions))
      } yield (usshs.userId, usshs.companySymbol, shs.companyName, usshs.quantity,
        shs.lastUpdate, shs.currency, shs.value, shs.sharesAvailable)
      res.results(searchOptions.numberOfResults).map(convRowsRec(_)).right.get
    }
  }

  override def getUserShare(userId: String, companySymbol: String): UserShare =
    db withDynSession {
      val res = for {
        (usshs, shs) <- userShares.filter(_.userId === userId).filter(_.companySymbol === companySymbol) join shares on (_.companySymbol === _.companySymbol)
      } yield (usshs.userId, usshs.companySymbol, shs.companyName, usshs.quantity,
        shs.lastUpdate, shs.currency, shs.value, shs.sharesAvailable)
      val row = res.first
      UserShare(row._1,Share(SharePrice(row._6,row._7),row._3,row._2,row._8,row._5),row._4)
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

  override def deleteUserShare(userId: String, shareId: String): ResponseCode =
    {
      db withDynSession userShares.filter(_.companySymbol===shareId).filter(_.userId===userId).delete.run
      ResponseCode.Updated(msg = "Deleted")
    }
}

