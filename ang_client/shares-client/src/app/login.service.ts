import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { UserLogin } from './user-login'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})

export class LoginService {
  ORIGIN = 'http://localhost:8080' + '/user';
  constructor(private httpClient: HttpClient) { }
  
  public getApiKey(userLogin: UserLogin) {
    var hash = this.hashPassword(userLogin.pwhash);
    var url = this.ORIGIN + '/login/?id=' + userLogin.userId + '&hash=' + hash;
    return this.httpClient.get(url);
  }

  private hashPassword(password) {return password}; //todo implement

  public createUser(user) {
    return this.httpClient.put<UserLogin>(this.ORIGIN + '/create' , user, httpOptions)
    .pipe(
    // catchError(this.handleError())
    ); //console.log('post user');
  }
}
