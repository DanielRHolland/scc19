package uk.co.danrh.scc.dal


import scala.slick.lifted.ColumnOrdered
import uk.co.danrh.scc.datatypes.{SearchOptions}

import scala.collection.immutable.Nil
import scala.slick.driver.SQLiteDriver.simple._

object OptionsUtil {
  def getSearchSharesBy(searchOptions: SearchOptions): Tables.Shares => Column[Boolean] = {
    val st = searchOptions.terms.head
    t: Tables.Shares =>
      (t.companyName like "%" + st + "%") ||
      (t.companySymbol like "%" + st + "%") ||
      (t.currency like "%" + st + "%")
  }

  def getSearchSharesUserSharesBy(searchOptions: SearchOptions): ((Tables.UserShares, Tables.Shares)) => Column[Boolean] = {
    val st = searchOptions.terms.head
    ts: (Tables.UserShares, Tables.Shares) =>
      (ts._2.companySymbol like "%" + st + "%") ||
      (ts._2.companyName like "%" + st + "%") ||
      (ts._2.currency like "%" + st + "%")
  }

  def isSortUserShares(searchOptions: SearchOptions) = {
    ("QuantityDesc":: "QuantityAsc" :: "CompanySymbolDesc" :: "CompanySymbolAsc" :: Nil )
      .contains(searchOptions.orderBy)
  }


  def getSortUserSharesBy(searchOptions: SearchOptions): Tables.UserShares => ColumnOrdered[_ >: Int with String] = {
    t:Tables.UserShares =>
    searchOptions.orderBy match {
      case "QuantityDesc" => t.quantity.desc
      case "QuantityAsc" => t.quantity.asc
      case "CompanySymbolDesc" => t.companySymbol.desc
      case _ => t.companySymbol.asc
    }
  }

  def getSortSharesBy(searchOptions: SearchOptions): Tables.Shares => ColumnOrdered[_ >: String with Int with Long with Double] = {
    t: Tables.Shares =>
      searchOptions.orderBy match {
        case "CompanyNameDesc" => t.companyName.desc
        case "CompanyNameAsc" => t.companyName.asc
        case "SharesAvailableDesc" => t.sharesAvailable.desc
        case "SharesAvailableAsc" => t.sharesAvailable.asc
        case "LastUpdateDesc" => t.lastUpdate.desc
        case "LastUpdateAsc" => t.lastUpdate.asc
        case "ValueDesc" => t.value.desc
        case "ValueAsc" => t.value.asc
        case "CurrencyDesc" => t.currency.desc
        case "CurrencyAsc" => t.currency.asc
        case "CompanySymbolDesc" => t.companySymbol.desc
        case _ => t.companySymbol.asc
      }
  }
}
