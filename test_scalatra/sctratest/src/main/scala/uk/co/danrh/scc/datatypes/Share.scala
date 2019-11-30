package uk.co.danrh.scc.datatypes

case class Share(
  sharePrice: SharePrice,
  companyName: String,
  companySymbol: String,
  numberOfSharesAvailable: Int,
  lastUpdate: Long
)
