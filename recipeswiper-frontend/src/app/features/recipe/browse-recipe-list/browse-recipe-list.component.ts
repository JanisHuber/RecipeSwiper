import { Component, Input } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';
import { RecipeCardSmallComponent } from '../recipe-card-small/recipe-card-small.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-browse-recipe-list',
  imports: [RecipeCardSmallComponent],
  templateUrl: './browse-recipe-list.component.html',
  styleUrl: './browse-recipe-list.component.css',
})
export class BrowseRecipeListComponent {
  @Input() recipes: Recipe[] = [];

  public numberOfShowedRecipes = 3;

  constructor(private router: Router) {}

  public showMoreRecipes() {
    this.numberOfShowedRecipes += 3;
  }

  public navigateToNewRecipe() {
    this.router.navigate(['/recipeswiper/new/Recipe']);
  }
}
