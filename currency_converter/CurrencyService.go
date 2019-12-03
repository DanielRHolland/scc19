package main

import (
	"encoding/csv"
	"encoding/json"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"os"
	"strconv"
)

type ExchangeRate struct {
	Id           string  `json:"Id"`
	CurrencyName string  `json:"CurrencyName"`
	RateInGbp    float64 `json:"RateInGbp"`
}


func loadRates() []ExchangeRate {
	lines, err := ReadCsv("exchange_rates.csv")
	if err != nil {
		panic(err)
	}
	var rates []ExchangeRate
	for _, line := range lines {
		rate,err:=strconv.ParseFloat(line[2],64)
		if err != nil {
			panic(err)
		}
		rates = append(rates, ExchangeRate{
			Id:           line[0],
			CurrencyName: line[1],
			RateInGbp:    rate,
		})
	}
	return rates
}

func ReadCsv(filename string) ([][]string, error) {
	f, err := os.Open(filename)
	if err != nil {
		return [][]string{}, err
	}
	defer f.Close()
	lines, err := csv.NewReader(f).ReadAll()
	if err != nil {
		return [][]string{}, err
	}

	return lines, nil
}

var rates = loadRates()

func getRates(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(rates)
}

func getCurrencyCodes(w http.ResponseWriter, r *http.Request) {
	var out []string
	for i:=0;i< len(rates);i++  {
		out = append(out, rates[i].Id)
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(out)
}

func getConversionRate(w http.ResponseWriter, r *http.Request)  {
	c1Id := mux.Vars(r)["c1"]
	c2Id := mux.Vars(r)["c2"]
	var curr1 float64 = -1
	var curr2 float64 = -1
	for i:=0;i<len(rates)&&(curr1==-1||curr2==-1) ;i++  {
		if rates[i].Id == c1Id {curr1=rates[i].RateInGbp}
		if rates[i].Id == c2Id {curr2=rates[i].RateInGbp}
	}
	w.Header().Set("Content-Type", "application/json")
	if curr2==-1 || curr1==-1 {
		http.Error(w,"Bad arguments", 404)
	} else {
		json.NewEncoder(w).Encode(curr1 / curr2)
	}
}

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/rates", getRates).Methods("GET")
	router.HandleFunc("/codes", getCurrencyCodes).Methods("GET")
	router.HandleFunc("/rate/{c1}/{c2}", getConversionRate).Methods("GET")
	log.Fatal(http.ListenAndServe(":8050", router))
}