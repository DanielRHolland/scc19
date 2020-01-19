import { Component, Output, EventEmitter } from '@angular/core';
import { LoginService } from '../login.service';
import { UserLogin } from '../user-login';
import { Router } from "@angular/router";
import { ApiKeyService } from '../api-key.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  model = new UserLogin('',''); 
  mode = 'login'; 
  constructor( private loginService: LoginService, private router: Router, private apiKeyService: ApiKeyService ) { }
  
  onSubmit() { 
    if (this.mode == 'login') {
      this.login(this.model);
    } else {
      this.loginService.createUser(this.model).subscribe(data => this.login(data['obj']));
    }
  }

  login(model) {
    this.loginService.getApiKey(model).subscribe(data => {
      this.apiKeyService.change(data['key']);
      this.router.navigate(['usershares']); 
    });
  }

}
