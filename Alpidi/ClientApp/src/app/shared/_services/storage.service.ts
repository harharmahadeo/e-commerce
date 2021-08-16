import { Injectable } from '@angular/core';
import { Subject, Subscription } from 'rxjs';

@Injectable()
export class StorageService {

  subscriptions: { [name: string]: Subject<any> } = {};

  constructor() {
    window.addEventListener('storage', event => this.onStorageChange(event));
  }



  private onStorageChange(event: StorageEvent) {
    const subject = this.subscriptions[event.key];

    if (!event.key) {
      localStorage.clear();
    }

    if (subject) {
      subject.next(event.oldValue);
    }
  }


  subscribe(name: string, callback: (oldValue) => void): Subscription {
    let subject = this.subscriptions[name];

    if (!subject) {
      subject = new Subject<any>();
      this.subscriptions[name] = subject;
    }

    return subject.subscribe(callback);
  }



}
