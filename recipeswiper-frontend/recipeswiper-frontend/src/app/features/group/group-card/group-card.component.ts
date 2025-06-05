import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from '../../../core/models/dto/Group';

@Component({
  selector: 'app-group-card',
  standalone: true,
  imports: [],
  templateUrl: './group-card.component.html',
  styleUrl: './group-card.component.css'
})
export class GroupCardComponent {
  @Input() group!: Group;

  constructor(private router: Router) {}

  navigateToSwipe() {
    this.router.navigate([`/recipeswiper/group/${this.group.groupToken}`]);
  }
}
