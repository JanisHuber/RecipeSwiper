import { Component, Input } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';

@Component({
  selector: 'app-recipe-card',
  standalone: true,
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.css',
})
export class RecipeCardComponent {
  @Input() recipe!: Recipe;
}
