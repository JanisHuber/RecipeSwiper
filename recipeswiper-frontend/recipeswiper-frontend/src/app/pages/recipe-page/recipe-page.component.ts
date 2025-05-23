import { Component } from '@angular/core';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { ActivatedRoute } from '@angular/router';
import { Recipe } from '../../core/models/Recipe';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recipe-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './recipe-page.component.html',
  styleUrl: './recipe-page.component.css'
})
export class RecipePageComponent {

  groupToken: string = '';
  recipes: Recipe[] = [];

  constructor(private recipeswiperService: RecipeswiperService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.groupToken = params['groupToken'];
    });
  }

  loadRecipes() {
    this.recipeswiperService.loadRecipes(this.groupToken);
  }

  getRecipes() {
    this.recipeswiperService.getRecipes(this.groupToken).subscribe(recipes => {
      this.recipes = recipes;
    });
  }
}
