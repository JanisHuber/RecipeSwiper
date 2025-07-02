import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-invite-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invite-popup.component.html',
  styleUrl: './invite-popup.component.css'
})

export class InvitePopupComponent {
  @Input() groupToken: string = '';
  @Output() close = new EventEmitter<void>();

  copied = false;

  copyToClipboard() {
    navigator.clipboard.writeText(this.groupToken).then(() => {
      this.copied = true;
      setTimeout(() => this.copied = false, 2000);
    });
  }

  onClose() {
    this.close.emit();
  }
}
