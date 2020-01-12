package main

import (
  "net/http"
  "fmt"
  "io/ioutil"
)

func getLiveRates(currencyCodes []string) {

fmt.Println(currencyCodes)
fmt.Println("Starting the application...")
var symbols string = "&symbols=USD,GBP"
for i := 0; i<0 && i<len(currencyCodes); i++ {
  if i != 0 {
    symbols += ","
  }
  symbols += currencyCodes[i]
}
fmt.Println(symbols)
    response, err := http.Get("https://api.exchangeratesapi.io/latest?base=GBP"+symbols)
    if err != nil {
        fmt.Printf("The HTTP request failed with error %s\n", err)
    } else {
        data, _ := ioutil.ReadAll(response.Body)
        fmt.Println(string(data))
    }
//GET  HTTP/1.1
}
