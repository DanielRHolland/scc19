import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserLogin } from './user-login'
import { ApiKeyService } from './api-key.service'

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  ORIGIN = 'http://localhost:8080' + '/user';
  constructor(private httpClient: HttpClient, private apiKeyService: ApiKeyService) { }
  
  public getApiKey(userLogin: UserLogin) {
    var hash = this.hashPassword(userLogin.password);
    var url = this.ORIGIN + '/login/?id=' + userLogin.username + '&hash=' + hash;
    var apiKey = this.httpClient.get(url).subscribe(data => 
    this.apiKeyService.change(data['key']));
    return apiKey;
  }

  private hashPassword(password) {return password}; //todo implement

  public createUser(user) {
    console.log('post user');//todo implement
  }
}
