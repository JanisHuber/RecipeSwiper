import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvitePopupComponent } from '../../../shared/components/invite-popup/invite-popup.component';


@Component({
  selector: 'app-invitemember',
  standalone: true,
  imports: [InvitePopupComponent],
  templateUrl: './invitemember.component.html',
  styleUrl: './invitemember.component.css'
})
export class InvitememberComponent {
  @Input() groupToken: string = '';

  showInvitePopup = false;

  inviteMember() {
    this.showInvitePopup = true;
  }
}
