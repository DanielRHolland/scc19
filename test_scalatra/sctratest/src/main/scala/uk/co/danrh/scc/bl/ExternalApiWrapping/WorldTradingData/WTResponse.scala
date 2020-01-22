package uk.co.danrh.scc.bl.ExternalApiWrapping.WorldTradingData

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(Array("message", "Message"))
case class WTResponse(symbols_requested: Int, symbols_returned: Int, data: List[WTDatum])
case class WTDatum(symbol: String,
                   name: String,
                   currency: String,
                   price: String,
                   price_open: String,
                   day_high:String,
                   day_low:String,
                   @JsonProperty("52_week_high") yearHigh:String,
                   @JsonProperty("52_week_low") yearLow:String,
                   day_change:String,
                   change_pct:String,
                   close_yesterday:String,
                   market_cap:String,
                   volume:String,
                   volume_avg:String,
                   shares:String,
                   stock_exchange_long:String,
                   stock_exchange_short:String,
                   timezone:String,
                   timezone_name:String,
                   gmt_offset:String,
                   last_trade_time:String,
                   pe:String,
                   eps:String)