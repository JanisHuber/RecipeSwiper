import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorPopupService } from '../../../core/services/error-popup.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-error-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error-popup.component.html',
  styleUrls: ['./error-popup.component.css'],
})
export class ErrorPopupComponent implements OnInit, OnDestroy {
  message: string = '';
  isVisible: boolean = false;
  isClosing: boolean = false;
  private subscription: Subscription;

  constructor(private errorPopupService: ErrorPopupService) {
    this.subscription = this.errorPopupService.message$.subscribe((message) => {
      this.message = message;
      this.show();
    });
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  show() {
    requestAnimationFrame(() => {
      this.isVisible = true;
      this.isClosing = false;
    });
    setTimeout(() => {
      this.startClosing();
    }, 4000);
  }

  close() {
    this.startClosing();
  }

  private startClosing() {
    this.isClosing = true;
    setTimeout(() => {
      this.isVisible = false;
    }, 300);
  }
}
