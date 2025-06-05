import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipeswiperService } from '../../core/services/recipeswiper.service';
import { UserService } from '../../core/services/user.service';
import { VoteSummaryComponent } from '../../features/voting/vote-summary/vote-summary.component';
import { RecipeResult } from '../../core/models/RecipeResult';
import { Router, ActivatedRoute } from '@angular/router';
import { InvitememberComponent } from '../../features/group/invitemember/invitemember.component';

@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [CommonModule, VoteSummaryComponent, InvitememberComponent],
  templateUrl: './group-page.component.html',
  styleUrl: './group-page.component.css',
})
export class GroupPageComponent {
  @Input() groupName: String = '';
  @Input() resultRecipes: RecipeResult[] = [];

  showInvitePopup = false;
  groupToken: string = '';

  constructor(
    private recipeswiperService: RecipeswiperService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.groupToken = this.route.snapshot.params['groupToken'];
    if (this.groupToken) {
      this.recipeswiperService
        .getResultRecipes(this.groupToken)
        .subscribe((recipes) => {
          this.resultRecipes = recipes;
          console.log(this.resultRecipes);
        });
    }
  }

  startSwiping() {
    this.router.navigate([`/recipeswiper/recipe/${this.groupToken}`]);
  }
}
