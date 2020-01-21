package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"github.com/gorilla/mux"
	"log"
	"net/http"
)

func enableCors(w *http.ResponseWriter) {
	(*w).Header().Set("Access-Control-Allow-Origin", "*")
}

func getRates(w http.ResponseWriter, r *http.Request) {
	enableCors(&w)
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(getDaysRates())
}

func getCurrencyCodes() []string {
	rates := getDaysRates()
	var out []string
	for i := 0; i < len(rates); i++ {
		out = append(out, rates[i].Id)
	}
	return out
}

func currencyCodes(w http.ResponseWriter, r *http.Request) {
	enableCors(&w)
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(getCurrencyCodes())
}

func getConversionRate(w http.ResponseWriter, r *http.Request) {
	enableCors(&w)
	c1Id := mux.Vars(r)["c1"]
	c2Id := mux.Vars(r)["c2"]
	rates := getDaysRates()
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
	enableCors(&w)
	w.WriteHeader(http.StatusOK)
	fmt.Fprintf(w, "Available methods:\n/rates \n/codes\n/rate/{currency1}/{currency2} e.g. /rate/AER/USD")
}

func setupRestApi(port string) {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/rates", getRates).Methods("GET")
	router.HandleFunc("/codes", currencyCodes).Methods("GET")
	router.HandleFunc("/rate/{c1}/{c2}", getConversionRate).Methods("GET")
	router.HandleFunc("/", getIndex).Methods("GET")
	log.Fatal(http.ListenAndServe(":"+port, router))
}

func main() {
	port := flag.String("port", "8050", "the port on which to serve the server")
	flag.Parse()
	setupRestApi(*port)
}
