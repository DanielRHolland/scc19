package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"reflect"
	"time"
)

type ExchangeRate struct {
	Id        string  `json:"Id"`
	RateInGbp float64 `json:"RateInGbp"`
}

var latestRates []ExchangeRate
var lastUpdateTime = time.Unix(0, 0)

type RatesJsonMapping struct {
	Base  string
	Date  string
	Rates map[string]float64
}

func getDaysRates() []ExchangeRate {
	if lastUpdateTime.AddDate(0, 0, 1).Before(time.Now()) {
		response, err := http.Get("https://api.exchangeratesapi.io/latest?base=GBP")
		if err != nil {
			fmt.Printf("The HTTP request failed with error %s\n", err)
		} else {
			var ratesJsonMapping RatesJsonMapping
			data, _ := ioutil.ReadAll(response.Body)
			json.Unmarshal(data, &ratesJsonMapping)
			keys := reflect.ValueOf(ratesJsonMapping.Rates).MapKeys()
			newRates := make([]ExchangeRate, len(keys))
			for i := 0; i < len(keys); i++ {
				keyStr := keys[i].String()
				newRates[i] = ExchangeRate{
					Id:        keyStr,
					RateInGbp: ratesJsonMapping.Rates[keyStr],
				}
			}
			lastUpdateTime = time.Now()
			latestRates = newRates
		}
	}
	return latestRates
}
