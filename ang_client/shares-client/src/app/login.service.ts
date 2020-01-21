import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { UserLogin } from './user-login';
import { IpService } from './ip.service'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private httpClient: HttpClient, private ipService: IpService) { }
  
  public getApiKey(userLogin: UserLogin) {
    var hash = this.hashPassword(userLogin.pwhash);
    var url = this.getIp() + '/login/?id=' + userLogin.userId + '&hash=' + hash;
    return this.httpClient.get(url);
  }

  private hashPassword(password) {return password}; //todo implement

  public createUser(user) {
    return this.httpClient.put<UserLogin>(this.getIp() + '/create' , user, httpOptions)
    .pipe(
    // catchError(this.handleError())
    ); //console.log('post user');
  }

  private getIp() {
    return this.ipService.getSharesValue() + '/user';
  }
}
