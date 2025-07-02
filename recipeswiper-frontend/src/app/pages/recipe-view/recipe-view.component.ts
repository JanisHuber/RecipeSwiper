import { Component } from '@angular/core';
import { RecipeswiperService } from '../../core/services/recipeswiper.service';
import { ActivatedRoute } from '@angular/router';
import { Recipe } from '../../core/models/Recipe';
import { RecipeFullComponent } from '../../features/recipe/recipe-full/recipe-full.component';


@Component({
  selector: 'app-recipe-view',
  imports: [RecipeFullComponent],
  templateUrl: './recipe-view.component.html',
  styleUrl: './recipe-view.component.css',
})
export class RecipeViewComponent {
  recipe!: Recipe;
  
  constructor(private recipeswiperService: RecipeswiperService, private activatedRoute: ActivatedRoute) {}
  
  ngOnInit() {
    this.recipeswiperService.getRecipeById(this.activatedRoute.snapshot.params['recipeId']).subscribe((recipe) => {
      this.recipe = recipe;
    });
  }
}
