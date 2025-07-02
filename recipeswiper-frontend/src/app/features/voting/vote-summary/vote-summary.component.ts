import { Component, Input } from '@angular/core';
import { RecipeResult } from '../../../core/models/RecipeResult';
import { Router } from '@angular/router';


@Component({
  selector: 'app-vote-summary',
  standalone: true,
  imports: [],
  templateUrl: './vote-summary.component.html',
  styleUrl: './vote-summary.component.css',
})
export class VoteSummaryComponent {
  @Input() resultRecipes: RecipeResult[] = [];

  public numberOfShowedRecipes = 3;

  constructor(private router: Router) {}

  public showMoreRecipes() {
    this.numberOfShowedRecipes += 3;
  }

  public viewRecipe(recipeId: number) {
    console.log(recipeId);
    console.log(this.router);
    this.router.navigate(['/recipeswiper/recipe/view/' + recipeId]);
  }
}
