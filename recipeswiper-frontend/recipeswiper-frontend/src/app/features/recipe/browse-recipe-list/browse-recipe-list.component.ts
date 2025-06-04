import { Component, Input } from '@angular/core';
import { Recipe } from '../../../core/models/Recipe';
import { RecipeCardSmallComponent } from '../recipe-card-small/recipe-card-small.component';


@Component({
  selector: 'app-browse-recipe-list',
  imports: [RecipeCardSmallComponent],
  templateUrl: './browse-recipe-list.component.html',
  styleUrl: './browse-recipe-list.component.css'
})
export class BrowseRecipeListComponent {
  @Input() recipes: Recipe[] = [];

  public numberOfShowedRecipes = 3;

  public showMoreRecipes() {
    this.numberOfShowedRecipes += 3;
  }
}
