package uk.co.danrh.scc.bl

import java.math.BigInteger

import uk.co.danrh.scc.datatypes.{ApiKey, ResponseCode}
import java.security.MessageDigest

trait ApiKeyBl {

  private def md5(s: String): String = new BigInteger(1,MessageDigest.getInstance("MD5").digest(s.getBytes)).toString()
  case class SessionKey(key: String, userId: String)
  private var sessionKeys = List[SessionKey](SessionKey("58653757563650904513105985680006753395","w"))//todo use more appropriate data structure

  def createKey(id:String,hash:String):Either[ResponseCode.Failed, ApiKey] = {
    if (sessionKeys.exists(_.userId == id)) Right(getApiKey(id))
    else if (UsersBl.getUser(id).pwhash==hash) {
      val key = SessionKey( md5(id + hash), id)
      sessionKeys = key :: sessionKeys
      Right(ApiKey(key.key))
    } else Left(ResponseCode.Failed())
  }

  def checkKeyValid(key: ApiKey): Boolean = sessionKeys.exists(_.key == key.key)

  def getUserId(key: String): String = sessionKeys.filter(_.key == key).head.userId

  private def getApiKey(userId: String): ApiKey = ApiKey(sessionKeys.filter(_.userId == userId).head.key)
}

object ApiKeyBl extends ApiKeyBl
