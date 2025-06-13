package ch.janishuber.recipeswiper.adapter.mealdb;

import ch.janishuber.recipeswiper.domain.Recipe;
import ch.janishuber.recipeswiper.domain.api.Tags;

import java.util.List;

public interface RecipeApi {
    Recipe getRandomRecipe();

    List<Recipe> getRecipesByTags(List<Tags> tags);
}