package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.SearchOptions

import scala.slick.driver.SQLiteDriver

object OptionsUtil {
  def isSortUserShares(searchOptions: SearchOptions) = {
    ("QuantityDesc":: "QuantityAsc" :: "CompanySymbolDesc" :: "CompanySymbolAsc" :: Nil )
      .contains(searchOptions.orderBy)
  }


  def getSortUserSharesBy(searchOptions: SearchOptions)= {
    t:Tables.UserShares =>
    searchOptions.orderBy match {
      case "QuantityDesc" => t.quantity.desc
      case "QuantityAsc" => t.quantity.asc
      case "CompanySymbolDesc" => t.companySymbol.desc
      case _ => t.companySymbol.asc
    }
  }

  def getSortSharesBy(searchOptions: SearchOptions) = {
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
        case _ => t.companySymbol.asc
      }
  }
}
