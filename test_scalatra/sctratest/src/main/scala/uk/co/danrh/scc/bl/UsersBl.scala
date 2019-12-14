package uk.co.danrh.scc.bl

import uk.co.danrh.scc.dal.UsersDal
import uk.co.danrh.scc.datatypes.{ResponseCode, User}

trait UsersBl {
  def getUser(id:String): User = UsersDal.getUser(id)
  def createUser(user: User): ResponseCode = UsersDal.insertOrUpdateUser(user)
}

object UsersBl extends UsersBl
