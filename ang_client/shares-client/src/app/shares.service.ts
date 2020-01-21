import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Purchase } from './models/purchase.model';
import { ApiKeyService } from './api-key.service';
import { IpService } from './ip.service'


  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };


@Injectable({
  providedIn: 'root'
})


export class SharesService {
  
  constructor(private httpClient: HttpClient, private apiKeyService: ApiKeyService, private ipService: IpService) { }


  public getShares() {
    return this.httpClient.get(this.getIp() + '/list' + this.keyExt());
  }

  public getUserShares(orderBy: string = "default", searchTerms: string = "", count: number = 10) {
    return this.httpClient.get(this.getIp() + '/user' + this.keyExt() + '&ob=' + orderBy + '&st='+searchTerms + '&number=' + count.toString());
  }

  public buyShare(purchase: Purchase) {
    return this.httpClient.post<Purchase>(this.getIp() + '/purchase' + this.keyExt(), purchase, httpOptions)
      .pipe(
      // catchError(this.handleError())
      ); 
      }

  private handleError() {
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };
  private getIp() { return this.ipService.getSharesValue() + '/share'}
  private keyExt () { return '?key='+ this.apiKeyService.getValue(); }
}
