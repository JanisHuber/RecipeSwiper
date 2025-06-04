package ch.janishuber.recipeswiper.adapter.rest.dto;

import ch.janishuber.recipeswiper.domain.Recipe;
import ch.janishuber.recipeswiper.domain.VoteResult;

public record RecipeResult(int recipeId, String title, String description, String ingredients, String instructions, String image_url, VoteResult voteResult) {

    public static RecipeResult ofRecipe(Recipe recipe, VoteResult voteResult) {
        return new RecipeResult(
                recipe.recipeId(),
                recipe.title(),
                recipe.description(),
                recipe.ingredients(),
                recipe.instructions(),
                recipe.image_url(),
                voteResult
        );
    }
}