import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class IpService {
  constructor() { }

  private sharesSource = new BehaviorSubject('http://localhost:8080');//DigitalOcean: http://159.65.81.247:8080
  sharesCurrent = this.sharesSource.asObservable();

  sharesChange(ip: string) {
    this.sharesSource.next(ip);
  }

  getSharesValue() {
    return this.sharesSource.getValue();
  }


}
