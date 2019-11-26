package uk.co.danrh.scc.datatypes

import java.util.Date

case class Share(
  sharePrice: SharePrice,
  companyName: String,
  companySymbol: String,
  numberOfSharesAvailable: Int,
  lastUpdate: Date
)
