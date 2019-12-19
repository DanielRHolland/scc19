package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"github.com/gorilla/mux"
	"log"
	"net/http"
)

var rates []ExchangeRate

func getRates(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(rates)
}

func getCurrencyCodes(w http.ResponseWriter, r *http.Request) {
	var out []string
	for i := 0; i < len(rates); i++ {
		out = append(out, rates[i].Id)
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(out)
}

func getConversionRate(w http.ResponseWriter, r *http.Request) {
	c1Id := mux.Vars(r)["c1"]
	c2Id := mux.Vars(r)["c2"]
	var curr1 float64 = -1
	var curr2 float64 = -1
	for i := 0; i < len(rates) && (curr1 == -1 || curr2 == -1); i++ {
		if rates[i].Id == c1Id {
			curr1 = rates[i].RateInGbp
		}
		if rates[i].Id == c2Id {
			curr2 = rates[i].RateInGbp
		}
	}
	w.Header().Set("Content-Type", "application/json")
	if curr2 == -1 || curr1 == -1 {
		http.Error(w, "Bad arguments", 404)
	} else {
		json.NewEncoder(w).Encode(curr1 / curr2)
	}
}

func getIndex(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	fmt.Fprintf(w, "Available methods:\n/rates \n/codes\n/rate/{currency1}/{currency2} e.g. /rate/AER/USD")
}

func main() {
	ratesFile := flag.String("file", "exchange_rates.csv", "The path of the exchange rates csv file")
	port := flag.String("port", "8050", "the port on which to serve the server")
	flag.Parse()
	rates = loadRates(*ratesFile)
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/rates", getRates).Methods("GET")
	router.HandleFunc("/codes", getCurrencyCodes).Methods("GET")
	router.HandleFunc("/rate/{c1}/{c2}", getConversionRate).Methods("GET")
	router.HandleFunc("/", getIndex).Methods("GET")
	log.Fatal(http.ListenAndServe(":"+*port, router))
}
