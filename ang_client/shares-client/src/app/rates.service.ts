import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const RATESORIGIN = 'http://159.65.81.247:8050';

@Injectable({
  providedIn: 'root'
})

export class RatesService {

  constructor(private httpClient: HttpClient) { }

  public getRates() {
    //return this.httpClient.get('http://localhost:8050/rates');
    return this.httpClient.get(RATESORIGIN + '/rates');
  }

  public getRate(c1: string, c2: string) {
    return this.httpClient.get(RATESORIGIN + '/rate/'+c1+'/'+c2);
  }
}
