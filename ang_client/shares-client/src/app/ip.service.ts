import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class IpService {
  constructor() { }

  private sharesSource = new BehaviorSubject('http://localhost:8080');
  sharesCurrent = this.sharesSource.asObservable();

  sharesChange(ip: string) {
    this.sharesSource.next(ip);
  }

  getSharesValue() {
    return this.sharesSource.getValue();
  }


  private ratesSource = new BehaviorSubject('http://159.65.81.247:8050');
  ratesCurrent = this.ratesSource.asObservable();

  ratesChange(ip: string) {
    return this.ratesSource.next(ip);
  }

  getRatesValue() {
    return this.ratesSource.getValue();
  }

}
