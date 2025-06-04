import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-vote-bar',
  imports: [],
  templateUrl: './vote-bar.component.html',
  styleUrl: './vote-bar.component.css'
})
export class VoteBarComponent {
  @Output() like = new EventEmitter<void>();
  @Output() dislike = new EventEmitter<void>();

  onLike() {
    this.like.emit();
  }

  onDislike() {
    this.dislike.emit();
  }
}
