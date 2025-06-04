import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';

@Component({
  selector: 'app-recipe-list',
  imports: [RecipeCardComponent],
  standalone: true,
  templateUrl: './recipe-list.component.html',
  styleUrl: './recipe-list.component.css'
})
export class RecipeListComponent {
  @Input() recipes: Recipe[] = [];
  @Input() currentRecipe: Recipe | null = null;
  @Input() currentIndex: number = 0;
  @Input() groupToken: string = '';
  
  @Output() like = new EventEmitter<void>();
  @Output() dislike = new EventEmitter<void>();

  onLike() {
    this.like.emit();
  }

  onDislike() {
    this.dislike.emit();
  }
}
