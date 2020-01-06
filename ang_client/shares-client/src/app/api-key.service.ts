import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class ApiKeyService {
  private source = new BehaviorSubject('');
  current = this.source.asObservable();

  constructor() { }

  change(apiKey: string) {
    this.source.next(apiKey);
  }

  getValue() {
    return this.source.getValue();
  }
}
