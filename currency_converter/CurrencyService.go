package main

import (
	"encoding/json"
//	"flag"
	"fmt"
	"github.com/gorilla/mux"
	"log"
	"net/http"
        "errors"
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
        rate, err := calcConvRate(mux.Vars(r)["c1"], mux.Vars(r)["c2"])
        
	if err != nil {
		http.Error(w, err.Error(), 404)
	} else {
                w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(rate)
	}
}

func calcConvRate(curr1Id string, curr2Id string ) (float64 , error) {
       rates := getDaysRates()
	var curr1 float64 = -1
	var curr2 float64 = -1
	for i := 0; i < len(rates) && (curr1 == -1 || curr2 == -1); i++ {
		if rates[i].Id == curr1Id {
			curr1 = rates[i].RateInGbp
		}
		if rates[i].Id == curr2Id {
			curr2 = rates[i].RateInGbp
		}
	}
        if curr2 == -1 || curr1 == -1 {
                return 0, errors.New("Failed to find rate for currencies")
        } else {
                return curr1/curr2, nil
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
	//port := flag.String("port", "8050", "the port on which to serve the rest server")
	//flag.Parse()
	//setupRestApi(*port)
        setupGrpcApi()
}
