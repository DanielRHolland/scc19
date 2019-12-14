package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.{Share, SharePrice, User}

import scala.slick.driver.SQLiteDriver
import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.lifted.ProvenShape
object Tables {
  val db: SQLiteDriver.backend.DatabaseDef = Database.forURL(
    "jdbc:sqlite:shares.db",
    driver = "org.sqlite.JDBC")


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

  def convertRowToShare(row:SharesRow): Share =
    Share(SharePrice(row._5,row._6), row._2, row._1, row._3, row._4)

  val shares: TableQuery[Shares] = TableQuery[Shares]


  type UsersRow = (String, String)
  class Users(tag: Tag) extends Table[UsersRow](tag, "users"){
    def userId: Column[String] = column[String]("user_id")
    def pwhash: Column[String] = column[String]("pwhash")

    override def * : ProvenShape[UsersRow] = (userId,pwhash)
  }

  def convertRowToUser(row: UsersRow): User = User(row._1, row._2)

  val users: TableQuery[Users] = TableQuery[Users]
}