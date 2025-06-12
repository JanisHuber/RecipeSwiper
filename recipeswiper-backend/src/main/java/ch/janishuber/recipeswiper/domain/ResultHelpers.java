package ch.janishuber.recipeswiper.domain;

import ch.janishuber.recipeswiper.adapter.persistence.GroupRecipesRepository;
import ch.janishuber.recipeswiper.adapter.persistence.RecipeVotesRepository;
import ch.janishuber.recipeswiper.adapter.persistence.entity.VoteType;
import ch.janishuber.recipeswiper.adapter.rest.dto.RecipeResult;
import ch.janishuber.recipeswiper.domain.api.Tags;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultHelpers {

    public static List<RecipeResult> sortMatchesFromResults(List<RecipeResult> recipeResultList) {
        return recipeResultList.stream()
                .sorted(Comparator.comparingInt((RecipeResult recipeResult) -> recipeResult.voteResults().stream()
                        .mapToInt(voteResult -> switch (voteResult.vote()) {
                            case VoteType.LIKE -> 1;
                            case VoteType.DISLIKE -> -1;
                        })
                        .sum()).reversed())
                .toList();
    }

    public static List<Tags> getFavoriteCategories(String groupToken, GroupRecipesRepository groupRecipesRepository, RecipeVotesRepository recipeVotesRepository) {
        List<Recipe> recipes = groupRecipesRepository.getAllRecipes(groupToken);
        if (recipes.isEmpty()) {
            return List.of();
        }

        List<RecipeResult> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            List<VoteResult> voteOfRecipe = recipeVotesRepository.getRecipeVoteFromGroup(groupToken, recipe.recipeId());
            RecipeResult recipeResult = RecipeResult.ofRecipe(recipe, voteOfRecipe);
            results.add(recipeResult);
        }

        List<RecipeResult> sortedResults = ResultHelpers.sortMatchesFromResults(results);

        List<Tags> tags = new ArrayList<>();
        sortedResults.forEach(recipe -> {
            tags.add(Tags.valueOf(recipe.description().toUpperCase()));
        });
        return tags;
    }
}
