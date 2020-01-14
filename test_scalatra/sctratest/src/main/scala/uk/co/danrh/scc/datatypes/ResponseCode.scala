package uk.co.danrh.scc.datatypes

sealed abstract class ResponseCode extends Product with Serializable

object ResponseCode  {
  final case class Created(msg:String = "Created", obj: Object = null) extends ResponseCode
  final case class Failed(msg:String = "Failed", obj: Object = null) extends ResponseCode
  final case class Updated(msg:String = "Updated", obj: Object = null) extends ResponseCode
}
