import { Component, Output, EventEmitter } from '@angular/core';
import { LoginService } from '../login.service';
import { UserLogin } from '../user-login';
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  model = new UserLogin('',''); 
  mode = 'login'; 
  @Output() notify: EventEmitter<string> = new EventEmitter<string>();

  constructor( private loginService: LoginService, private router: Router) { }
  
  onSubmit() { console.log(this.mode); this.login(); this.router.navigate(['usershares']); }

  login() {
    this.loginService.getApiKey(this.model);
  }

}
