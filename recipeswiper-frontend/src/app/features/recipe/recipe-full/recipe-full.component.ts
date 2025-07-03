import { Component, Input } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';

@Component({
  selector: 'app-recipe-full',
  standalone: true,
  imports: [],
  templateUrl: './recipe-full.component.html',
  styleUrl: './recipe-full.component.css',
})
export class RecipeFullComponent {
  @Input() recipe!: Recipe;
}
