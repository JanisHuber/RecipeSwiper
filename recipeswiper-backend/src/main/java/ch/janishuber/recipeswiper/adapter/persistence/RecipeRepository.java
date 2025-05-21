package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.RecipeEntity;
import ch.janishuber.recipeswiper.domain.Recipe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@RequestScoped
public class RecipeRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    @Transactional
    public void save(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity(recipe.title(), recipe.description(), recipe.image_url(), recipe.ingredients(), recipe.instructions());
        em.persist(recipeEntity);
        em.flush();
    }

    @Transactional
    public Optional<Recipe> getRecipe(int recipeId) {
        RecipeEntity entity = em.find(RecipeEntity.class, recipeId);
        if (entity == null) {
            return Optional.empty();
        }
        Recipe recipe = new Recipe(entity.getRecipeId(), entity.getTitle(), entity.getDescription(), entity.getIngredients(), entity.getInstructions(), entity.getImageUrl());
        return Optional.of(recipe);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        RecipeEntity entity = em.find(RecipeEntity.class, recipe.recipeId());
        entity.setTitle(recipe.title());
        entity.setDescription(recipe.description());
        entity.setImageUrl(recipe.image_url());
        entity.setIngredients(recipe.ingredients());
        entity.setInstructions(recipe.instructions());
    }
}
