import { Component, OnInit } from '@angular/core';
import { RecipeswiperService } from '../../core/services/recipeswiper.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from '../../core/models/Recipe';
import { CommonModule } from '@angular/common';
import { RecipeListComponent } from '../../features/recipe/recipe-list/recipe-list.component';
import { VoteType } from '../../core/models/VoteType';
import { ErrorPopupService } from '../../core/services/error-popup.service';


@Component({
  selector: 'app-recipe-page',
  standalone: true,
  imports: [CommonModule, RecipeListComponent],
  templateUrl: './recipe-page.component.html',
  styleUrl: './recipe-page.component.css',
})
export class RecipePageComponent implements OnInit {
  groupToken: string = '';
  recipes: Recipe[] = [];
  currentRecipe: Recipe | null = null;
  currentIndex: number = 0;
  errorMessage: string = '';

  constructor(
    private recipeswiperService: RecipeswiperService,
    private route: ActivatedRoute,
    private router: Router,
    private errorPopupService: ErrorPopupService
  ) {
    this.route.params.subscribe((params) => {
      this.groupToken = params['groupToken'];
    });
  }

  ngOnInit() {
    this.getRecipes();
  }

  loadRecipes() {
    this.recipeswiperService.loadRecipes(this.groupToken);
    setTimeout(() => {
      this.getRecipes();
    }, 1000);
  }

  getRecipes() {
    this.recipeswiperService.getRecipesForUser(this.groupToken).subscribe({
      next: (recipes) => {
        this.recipes = recipes;
        this.currentIndex = 0;
        this.setCurrentRecipe();
      },
    });
  }

  private setCurrentRecipe() {
    if (this.recipes.length > 0 && this.currentIndex < this.recipes.length) {
      this.currentRecipe = this.recipes[this.currentIndex];
    } else {
      this.currentRecipe = null;
    }
  }

  likeRecipe() {
    if (this.currentRecipe) {
      this.recipeswiperService
        .vote(this.groupToken, this.currentRecipe.recipeId, VoteType.LIKE)
        .subscribe({
          next: () => {
            this.nextRecipe();
          },
          error: (error) => {
            this.errorPopupService.showError(error.message);
            this.nextRecipe();
          },
        });
    }
  }

  dislikeRecipe() {
    if (this.currentRecipe) {
      this.recipeswiperService
        .vote(this.groupToken, this.currentRecipe.recipeId, VoteType.DISLIKE)
        .subscribe({
          next: () => {
            this.nextRecipe();
          },
          error: (error) => {
            this.errorPopupService.showError(error.message);
            this.nextRecipe();
          },
        });
    }
  }

  private nextRecipe() {
    this.currentIndex++;
    this.setCurrentRecipe();
  }

  backToGroupOverview() {
    this.router.navigate([`/recipeswiper/group/${this.groupToken}`]);
  }
}
