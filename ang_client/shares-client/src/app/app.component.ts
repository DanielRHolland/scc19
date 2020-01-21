import { Component, OnInit } from '@angular/core';
import { ApiKeyService } from './api-key.service'
import { IpService } from './ip.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Shares Broker';
  apiKey;
  connectionInformation;
  ip;
  constructor(private apiKeyService: ApiKeyService, private ipService: IpService) {}

  

  ngOnInit() {
    this.apiKeyService.current.subscribe(apiKey => this.apiKey = apiKey);
    this.ipService.sharesCurrent.subscribe(ip => this.ip = ip);
  }

toggleConnectionInfo() {
  this.connectionInformation = !this.connectionInformation;
}
}
