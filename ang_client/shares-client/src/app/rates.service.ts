import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IpService } from './ip.service';

@Injectable({
  providedIn: 'root'
})

export class RatesService {

  constructor(private httpClient: HttpClient, private ipService: IpService) { }

  public getRates() {
    return this.httpClient.get(this.ipService.getRatesValue() + '/rates');
  }

  public getRate(c1: string, c2: string) {
    return this.httpClient.get(this.ipService.getRatesValue() + '/rate/'+c1+'/'+c2);
  }
}
