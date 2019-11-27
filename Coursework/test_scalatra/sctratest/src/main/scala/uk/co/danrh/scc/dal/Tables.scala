package uk.co.danrh.scc.dal

import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.lifted.ProvenShape
object Tables {
  type SharesRow = (String, String, Int, Long, String, Double)

  class Shares(tag: Tag) extends Table[SharesRow](tag, "shares"){
    def companySymbol: Column[String] = column[String]("company_symbol",O.PrimaryKey)
    def companyName: Column[String] = column[String]("company_name")
    def sharesAvailable: Column[Int] = column[Int]("shares_available")
    def lastUpdate: Column[Long] = column[Long]("last_update")
    def currency: Column[String] = column[String]("currency")
    def value: Column[Double] = column[Double]("value")

    override def * : ProvenShape[SharesRow] = (
      companySymbol,companyName,sharesAvailable,lastUpdate,currency,value)
  }
  val shares: TableQuery[Shares] = TableQuery[Shares]
}


//// Definition of the SUPPLIERS table
//class Suppliers(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
//  def id      = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
//  def name    = column[String]("SUP_NAME")
//  def street  = column[String]("STREET")
//  def city    = column[String]("CITY")
//  def state   = column[String]("STATE")
//  def zip     = column[String]("ZIP")
//
//  // Every table needs a * projection with the same type as the table's type parameter
//  def * = (id, name, street, city, state, zip)
//
//}
//val suppliers = TableQuery[Suppliers]