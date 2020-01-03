import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SharesService {

  constructor(private httpClient: HttpClient) { }

  public getRates() {
    return this.httpClient.get('http://159.65.81.247:8880/rates');
  }
}
