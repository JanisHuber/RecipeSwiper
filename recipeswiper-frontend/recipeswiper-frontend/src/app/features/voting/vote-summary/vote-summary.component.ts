import { Component, Input } from '@angular/core';
import { RecipeResult } from '../../../core/models/RecipeResult';

@Component({
  selector: 'app-vote-summary',
  imports: [],
  templateUrl: './vote-summary.component.html',
  styleUrl: './vote-summary.component.css'
})
export class VoteSummaryComponent {
  @Input() resultRecipes: RecipeResult[] = [];
}
