import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';


@Component({
  selector: 'app-recipe-list',
  imports: [],
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
