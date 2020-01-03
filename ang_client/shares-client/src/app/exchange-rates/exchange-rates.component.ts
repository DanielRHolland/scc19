import { Component, OnInit } from '@angular/core';
import { RatesService } from '../rates.service';

@Component({
  selector: 'app-exchange-rates',
  templateUrl: './exchange-rates.component.html',
  styleUrls: ['./exchange-rates.component.css']
})
export class ExchangeRatesComponent implements OnInit {
  rates;
  constructor(private ratesService: RatesService) { }

  ngOnInit() {
    this.ratesService.getRates().subscribe((data)=>{
        console.log(data);
        this.rates = data;
      }) 
  }

}
