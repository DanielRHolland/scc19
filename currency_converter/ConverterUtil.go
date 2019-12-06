package main

import (
	"encoding/csv"
	"os"
	"strconv"
)

type ExchangeRate struct {
	Id           string  `json:"Id"`
	CurrencyName string  `json:"CurrencyName"`
	RateInGbp    float64 `json:"RateInGbp"`
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

func loadRates(filename string) []ExchangeRate {
	lines, err := ReadCsv(filename)
	if err != nil {
		panic(err)
	}
	var rates []ExchangeRate
	for _, line := range lines {
		rate, err := strconv.ParseFloat(line[2], 64)
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
