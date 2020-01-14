package uk.co.danrh.scc.dal
import uk.co.danrh.scc.dal.Tables.{convertRowToUser, db, users}
import uk.co.danrh.scc.datatypes.{ResponseCode, User}

import scala.slick.driver.SQLiteDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession

trait UsersDalSqllite extends UsersDal {
  override def getUser(id: String): User = convertRowToUser(
    db.withDynSession(users.filter(_.userId === id).first))

  override def insertOrUpdateUser(user: User): ResponseCode = {
    db withDynSession {
      users.insertOrUpdate(
      user.userId,
      user.pwhash
      )
    }
  ResponseCode.Created(obj = user)
  }
}

