package uk.co.danrh.scc.datatypes

import uk.co.danrh.scc.datatypes

object ResponseCode extends Enumeration {
  type ResponseCode = Value
  val Created: datatypes.ResponseCode.Value = Value("Created")
  val Updated: datatypes.ResponseCode.Value = Value("Updated")
  val Failed: datatypes.ResponseCode.Value = Value("Failed")
}
