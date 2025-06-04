import { Component, OnInit } from '@angular/core';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from '../../core/models/Recipe';
import { CommonModule } from '@angular/common';
import { RecipeListComponent } from '../../features/recipe/recipe-list/recipe-list.component';


@Component({
  selector: 'app-recipe-page',
  standalone: true,
  imports: [CommonModule, RecipeListComponent],
  templateUrl: './recipe-page.component.html',
  styleUrl: './recipe-page.component.css'
})
export class RecipePageComponent implements OnInit {

  groupToken: string = '';
  recipes: Recipe[] = [];
  currentRecipe: Recipe | null = null;
  currentIndex: number = 0;


  constructor(private recipeswiperService: RecipeswiperService, private route: ActivatedRoute, private router: Router) {
    this.route.params.subscribe(params => {
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
    this.recipeswiperService.getRecipes(this.groupToken).subscribe({ 
      next: (recipes) => {
        this.recipes = recipes;
        this.currentIndex = 0;
        this.setCurrentRecipe();
      }
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
      console.log('Liked recipe:', this.currentRecipe.title);
      this.nextRecipe();
    }
  }

  dislikeRecipe() {
    if (this.currentRecipe) {
      console.log('Disliked recipe:', this.currentRecipe.title);
      this.nextRecipe();
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