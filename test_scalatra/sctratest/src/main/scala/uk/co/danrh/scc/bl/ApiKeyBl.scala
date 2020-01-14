package uk.co.danrh.scc.bl

import java.math.BigInteger
import java.nio.charset.StandardCharsets

import uk.co.danrh.scc.datatypes.{ApiKey, ResponseCode}
import java.security.MessageDigest

trait ApiKeyBl {


  def md5(s: String) = new String(new BigInteger(1,MessageDigest.getInstance("MD5").digest(s.getBytes)).toString())
  case class SessionKey(key: String, userId: String)
  private var sessionKeys = List[SessionKey]()//todo use more appropriate data structure

  def createKey(id:String,hash:String):Either[ResponseCode.Failed, ApiKey] = {
    if (UsersBl.getUser(id).pwhash==hash) {
      val key = SessionKey( md5(id + hash), id)
      sessionKeys = key :: sessionKeys
      Right(ApiKey(key.key))
    } else Left(ResponseCode.Failed())
  }

  def checkKeyValid(key: ApiKey):Boolean = sessionKeys.exists(_.key == key.key)

  def getUserId(key: String): String = sessionKeys.filter(_.key == key).head.userId
}

object ApiKeyBl extends ApiKeyBl
