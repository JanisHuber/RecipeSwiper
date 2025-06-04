import { Component } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-group',
  imports: [FormsModule],
  templateUrl: './create-group.component.html',
  styleUrl: './create-group.component.css',
})
export class CreateGroupComponent {
  groupName: string = '';

  constructor(private recipeswiperService: RecipeswiperService) {}

  createGroup() {
    if (!this.groupName) return;
    this.recipeswiperService.createGroup(this.groupName);
  }
}
