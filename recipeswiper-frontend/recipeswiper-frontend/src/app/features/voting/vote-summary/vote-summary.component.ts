import { Component, Input } from '@angular/core';
import { RecipeResult } from '../../../core/models/RecipeResult';

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

  public showMoreRecipes() {
    this.numberOfShowedRecipes += 3;
  }
}
