package uk.co.danrh.scc.bl

import java.math.BigInteger
import java.nio.charset.StandardCharsets

import uk.co.danrh.scc.datatypes.{ApiKey, ResponseCode}
import java.security.MessageDigest

trait ApiKeyBl {


  def md5(s: String) = new String(new BigInteger(1,MessageDigest.getInstance("MD5").digest(s.getBytes)).toString())

  private var session_keys = List[String]()//todo use more appropriate data structure

  def createKey(id:String,hash:String):Either[ResponseCode.Failed, ApiKey] = {
    if (UsersBl.getUser(id).pwhash==hash) {
      val key = md5(id + hash)
      session_keys = key :: session_keys
      Right(ApiKey(key))
    } else Left(ResponseCode.Failed())
  }

  def checkKeyValid(key: ApiKey):Boolean = true//session_keys.contains(key.key)
}

object ApiKeyBl extends ApiKeyBl
