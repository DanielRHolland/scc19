import { Component } from '@angular/core';
import { LoginService } from '../login.service';
import { UserLogin } from '../user-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  submitted = false;
  model = new UserLogin('',''); 
  constructor( private loginService: LoginService) { }
  

  onSubmit() { this.login(); this.submitted = true; }

  login() {
    this.loginService.getApiKey(this.model).subscribe( data => console.log(data['key'])
    );
  }

}
