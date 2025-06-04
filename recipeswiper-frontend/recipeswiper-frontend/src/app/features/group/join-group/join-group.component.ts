import { Component, Input } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-join-group',
  imports: [CommonModule, FormsModule],
  templateUrl: './join-group.component.html',
  styleUrl: './join-group.component.css'
})
export class JoinGroupComponent {
  groupCode: string = '';

  constructor(private recipeswiperService: RecipeswiperService) {}

  joinGroup() {
    if (!this.groupCode) return;
    
    this.recipeswiperService.joinGroup(this.groupCode);
  }
}
