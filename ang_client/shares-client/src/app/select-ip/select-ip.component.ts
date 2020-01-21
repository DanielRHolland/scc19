import { Component, OnInit } from '@angular/core';
import { IpService } from '../ip.service';

@Component({
  selector: 'app-select-ip',
  templateUrl: './select-ip.component.html',
  styleUrls: ['./select-ip.component.css']
})
export class SelectIpComponent implements OnInit {

  sharesIp;

  constructor(private ipService: IpService) { }

  ngOnInit() {
    this.sharesIp = this.ipService.getSharesValue();
  }

  setIps() {
    this.ipService.sharesChange(this.sharesIp);
  }

  setLocalhost() {
    this.sharesIp = 'http://localhost:8080';
    this.setIps();
  }

  setDigitalOcean() {
    this.sharesIp = 'http://159.65.81.247:8080';
    this.setIps();
  }

}
