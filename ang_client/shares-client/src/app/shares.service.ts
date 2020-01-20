import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Purchase } from './models/purchase.model';
import { ApiKeyService } from './api-key.service';


  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };


@Injectable({
  providedIn: 'root'
})


export class SharesService {
  ORIGIN = 'http://localhost:8080' + '/share';
  
  constructor(private httpClient: HttpClient, private apiKeyService: ApiKeyService) { }


  public getShares() {
    return this.httpClient.get(this.ORIGIN + '/list' + this.keyExt());
  }

  public getUserShares(orderBy: string = "default", searchTerms: string = "") {
    return this.httpClient.get(this.ORIGIN + '/user' + this.keyExt() + '&ob=' + orderBy + '&st='+searchTerms);
  }

  public buyShare(purchase: Purchase) {
    return this.httpClient.post<Purchase>(this.ORIGIN + '/purchase' + this.keyExt(), purchase, httpOptions)
      .pipe(
      // catchError(this.handleError())
      ); 
      }

  private handleError() {
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };

  private keyExt () { return '?key='+ this.apiKeyService.getValue(); }
}
