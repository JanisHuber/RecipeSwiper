import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Recipe } from '../../../core/models/Recipe';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recipe-card-small',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './recipe-card-small.component.html',
  styleUrls: ['./recipe-card-small.component.css'],
})
export class RecipeCardSmallComponent {
  @Input() recipe!: Recipe;
  @Output() view = new EventEmitter<void>();

  constructor(private router: Router) {}

  viewRecipe(recipeId: number) {
    this.router.navigate(['/recipeswiper/recipe/view/' + recipeId]);
  }
}
