import { Component, OnInit } from '@angular/core';
import { ApiKeyService } from './api-key.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Shares Broker';
  apiKey;

  constructor(private apiKeyService: ApiKeyService) {}

  onNotify(message:string):void {
    console.log(message)  
  }

  ngOnInit() {
    this.apiKeyService.current.subscribe(apiKey => this.apiKey = apiKey);
  }


}
