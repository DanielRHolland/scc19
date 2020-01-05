import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserLogin } from './user-login'

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  ORIGIN = 'http://localhost:8080' + '/user';
  constructor(private httpClient: HttpClient) { }
  
  public getApiKey(userLogin: UserLogin) {
    var hash = this.hashPassword(userLogin.password);
    var url = this.ORIGIN + '/login/?id=' + userLogin.username + '&hash=' + hash;
    return this.httpClient.get(url);
  }

  private hashPassword(password) {return password}; //todo implement

  public createUser(user) {
    console.log('post user');//todo implement
  }
}
