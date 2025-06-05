package ch.janishuber.recipeswiper.adapter.rest.dto;

import ch.janishuber.recipeswiper.domain.Recipe;
import ch.janishuber.recipeswiper.domain.VoteResult;
import java.util.List;


public record RecipeResult(int recipeId, String title, String description, String ingredients, String instructions, String image_url, List<VoteResult> voteResults) {

    public static RecipeResult ofRecipe(Recipe recipe, List<VoteResult> voteResults) {
        return new RecipeResult(
                recipe.recipeId(),
                recipe.title(),
                recipe.description(),
                recipe.ingredients(),
                recipe.instructions(),
                recipe.image_url(),
                voteResults
        );
    }
}