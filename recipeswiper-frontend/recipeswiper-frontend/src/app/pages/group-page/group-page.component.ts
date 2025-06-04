import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { UserService } from '../../core/services/user-service';
import { VoteSummaryComponent } from '../../features/voting/vote-summary/vote-summary.component';
import { RecipeResult } from '../../core/models/RecipeResult';


@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [CommonModule, VoteSummaryComponent],
  templateUrl: './group-page.component.html',
  styleUrl: './group-page.component.css'
})
export class GroupPageComponent {
  @Input() groupName: String = '';
  @Input() resultRecipes: RecipeResult[] = [];

  constructor(private recipeswiperService: RecipeswiperService, private userService: UserService) {}

  ngOnInit() {
    const groupToken = this.userService.getCurrentUser()?.groupToken;
    if (groupToken) {
      this.recipeswiperService.getResultRecipes(groupToken).subscribe(recipes => {
        this.resultRecipes = recipes;
      });
    }
  }
}
