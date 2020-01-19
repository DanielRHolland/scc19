package uk.co.danrh.scc.datatypes

case class SearchOptions(numberOfResults: Int = 10,terms: List[String] = Nil, orderBy: String = "default")
