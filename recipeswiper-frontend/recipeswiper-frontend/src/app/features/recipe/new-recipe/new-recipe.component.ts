import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Recipe } from '../../../core/models/Recipe';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';

export enum Tags {
  BEEF = 'BEEF',
  CHICKEN = 'CHICKEN',
  DESSERT = 'DESSERT',
  LAMB = 'LAMB',
  MISCELLANEOUS = 'MISCELLANEOUS',
  PASTA = 'PASTA',
  PORK = 'PORK',
  SEAFOOD = 'SEAFOOD',
  SIDE = 'SIDE',
  STARTER = 'STARTER',
  VEGAN = 'VEGAN',
  VEGETARIAN = 'VEGETARIAN',
  BREAKFAST = 'BREAKFAST',
  GOAT = 'GOAT',
}

@Component({
  selector: 'app-new-recipe',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './new-recipe.component.html',
  styleUrl: './new-recipe.component.css',
})
export class NewRecipeComponent {
  recipeForm: FormGroup;
  tags = Object.values(Tags);

  constructor(
    private fb: FormBuilder,
    private recipeswiperService: RecipeswiperService
  ) {
    this.recipeForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      instructions: ['', [Validators.required, Validators.minLength(10)]],
      ingredients: ['', [Validators.required, Validators.minLength(5)]],
      imageUrl: [
        '',
        [Validators.pattern(/^https?:\/\/.+\.(jpg|jpeg|png|gif|webp)$/i)],
      ],
      tag: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.recipeForm.valid) {
      const recipeData = this.recipeForm.value;
      const recipe: Recipe = {
        title: recipeData.title,
        instructions: recipeData.instructions,
        ingredients: recipeData.ingredients,
        description: recipeData.tag,
        image_url: recipeData.imageUrl || '',
        recipeId: 0,
      };
      this.recipeswiperService.createRecipe(recipe).subscribe((response) => {
        console.log(response);
        if (response === 'Recipe saved') {
          alert('Recipe successfully created!');
          this.recipeForm.reset();
        } else {
          alert('Recipe creation failed!');
        }
      });
    } else {
      alert('Please fill in all fields correctly.');
    }
  }

  getTagDisplayName(tag: string): string {
    switch (tag) {
      case 'BEEF':
        return 'Beef';
      case 'CHICKEN':
        return 'Chicken';
      case 'DESSERT':
        return 'Dessert';
      case 'LAMB':
        return 'Lamb';
      case 'MISCELLANEOUS':
        return 'Miscellaneous';
      case 'PASTA':
        return 'Pasta';
      case 'PORK':
        return 'Pork';
      case 'SEAFOOD':
        return 'Seafood';
      case 'SIDE':
        return 'Side';
      case 'STARTER':
        return 'Starter';
      case 'VEGAN':
        return 'Vegan';
      case 'VEGETARIAN':
        return 'Vegetarian';
      case 'BREAKFAST':
        return 'Breakfast';
      case 'GOAT':
        return 'Goat';
      default:
        return tag;
    }
  }
}
