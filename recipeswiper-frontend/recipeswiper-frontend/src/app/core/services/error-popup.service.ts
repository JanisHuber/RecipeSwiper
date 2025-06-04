import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorPopupService {
  private messageSubject = new Subject<string>();
  message$ = this.messageSubject.asObservable();

  showError(message: string) {
    this.messageSubject.next(message);
  }
}
