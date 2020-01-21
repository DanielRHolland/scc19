import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IpService } from './ip.service';
import { ApiKeyService } from './api-key.service' 

@Injectable({
  providedIn: 'root'
})

export class RatesService {

  constructor(private httpClient: HttpClient, private ipService: IpService, private apiKeyService: ApiKeyService) { }

  public getSymbols() {
    return this.httpClient.get(this.ipService.getSharesValue() + '/share/currencies' + this.keyExt());
  }

  public getRate(c1: string, c2: string) {
    return this.httpClient.get(this.ipService.getSharesValue() + '/share/convert/'+c1+'/'+c2 + this.keyExt());
  }

  private keyExt () { return '?key='+ this.apiKeyService.getValue(); }
}
