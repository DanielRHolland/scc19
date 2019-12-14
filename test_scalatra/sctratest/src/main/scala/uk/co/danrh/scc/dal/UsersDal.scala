package uk.co.danrh.scc.dal

import uk.co.danrh.scc.datatypes.{ResponseCode, User}

trait UsersDal {
  def insertOrUpdateUser(user: User): ResponseCode
  def getUser(id: String): User
}

object UsersDal extends UsersDalSqllite