package ch.janishuber.recipeswiper.application;

import ch.janishuber.recipeswiper.adapter.persistence.RecipeRepository;
import ch.janishuber.recipeswiper.domain.Recipe;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

@Dependent
public class RecipeService {

    @Inject
    RecipeRepository recipeRepository;
    
    public int save(Recipe recipe) {
        Optional<Recipe> foundRecipe = recipeRepository.find(recipe);
        if (foundRecipe.isPresent()) {
            return foundRecipe.get().recipeId();
        } else {
            return recipeRepository.save(recipe);
        }
    }
}
