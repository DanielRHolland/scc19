package uk.co.danrh.scc.dal
import uk.co.danrh.scc.datatypes.{ResponseCode, SearchOptions, Share, SharePrice}


trait SharesDalMock extends SharesDal {
  private val sharePrice = SharePrice("GBP",1000)
  private val d = 1574793014

  var shares = List(
    Share(sharePrice,"Company 1", "CY1",1000,d),
    Share(sharePrice,"Company 2", "CY2",890,d),
    Share(sharePrice,"Company 3", "CY3",300,d),
    Share(sharePrice,"Company 4", "CY4",200,d),
    Share(sharePrice,"Company 5", "CY5",700,d),
    Share(sharePrice,"Company 6", "CY6",650,d))

  override def getShares(searchOptions: SearchOptions = SearchOptions()): List[Share] = {
    val number = searchOptions.numberOfResults
    if (number== -1 || number>=shares.length) shares
    else shares.slice(0, number)
  }
  override def getShare(id:String): Share = getShares(SearchOptions(-1)).find(s => s.companySymbol == id).get
  override def insertOrUpdateShare(share: Share): ResponseCode = {
    shares = share :: shares
    ResponseCode.Created()
  }
}