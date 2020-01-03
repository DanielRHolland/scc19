import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SharesService {
  ORIGIN = 'http://localhost:8080';
  constructor(private httpClient: HttpClient) { }

  public getShares() {
    return this.httpClient.get(this.ORIGIN + '/share/list');
  }
}
