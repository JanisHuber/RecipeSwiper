package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.entity.RecipeEntity;
import ch.janishuber.recipeswiper.domain.Recipe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class RecipeRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public Optional<Recipe> find(Recipe recipe) {
        List<RecipeEntity> entities = em.createQuery("""
                        SELECT r FROM RecipeEntity r WHERE r.title = :title AND r.description = :description
                        """, RecipeEntity.class)
                .setParameter("title", recipe.title())
                .setParameter("description", recipe.description())
                .getResultList();

        if (entities.isEmpty()) {
            return Optional.empty();
        }

        RecipeEntity entity = entities.get(0);
        Recipe foundRecipe = new Recipe(entity.getRecipeId(), entity.getTitle(), entity.getDescription(),
                entity.getIngredients(), entity.getInstructions(), entity.getImageUrl());
        return Optional.of(foundRecipe);
    }

    @Transactional
    public int save(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity(recipe.title(), recipe.description(), recipe.image_url(),
                recipe.ingredients(), recipe.instructions());
        em.persist(recipeEntity);
        em.flush();
        return recipeEntity.getRecipeId();
    }

    public Optional<Recipe> getRecipe(int recipeId) {
        RecipeEntity entity = em.find(RecipeEntity.class, recipeId);
        if (entity == null) {
            return Optional.empty();
        }
        Recipe recipe = new Recipe(entity.getRecipeId(), entity.getTitle(), entity.getDescription(),
                entity.getIngredients(), entity.getInstructions(), entity.getImageUrl());
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

    public List<Recipe> getAllRecipes() {
        return em.createQuery("SELECT r FROM RecipeEntity r", RecipeEntity.class)
                .getResultList()
                .stream()
                .map(entity -> new Recipe(entity.getRecipeId(), entity.getTitle(), entity.getDescription(),
                        entity.getIngredients(), entity.getInstructions(), entity.getImageUrl()))
                .toList();
    }
}
