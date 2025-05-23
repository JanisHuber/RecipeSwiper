package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.GroupRecipesEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.RecipeEntity;
import ch.janishuber.recipeswiper.domain.Recipe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class GroupRecipesRepository {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    @Transactional
    public void addRecipeToGroup(int recipeId, int groupId) {
        GroupRecipesEntity groupRecipe = new GroupRecipesEntity(recipeId, groupId, 0);
        em.persist(groupRecipe);
        em.flush();
    }

    @Transactional
    public void addRecipeToGroup(int recipeId, String groupToken) {
        addRecipeToGroup(recipeId, convertTokenToGroupId(groupToken));
    }

    public List<Recipe> getAllRecipes(int groupId) {
        List<RecipeEntity> recipeEntities = em.createQuery("""
                            SELECT r FROM RecipeEntity r
                            JOIN GroupRecipesEntity gr ON r.id = gr.recipe_id
                            WHERE gr.group_id = :groupId
                        """, RecipeEntity.class)
                .setParameter("groupId", groupId)
                .getResultList();

        return recipeEntities.stream()
                .map(r -> new Recipe(
                        r.getRecipeId(),
                        r.getTitle(),
                        r.getDescription(),
                        r.getIngredients(),
                        r.getInstructions(),
                        r.getImageUrl()))
                .toList();
    }

    public List<Recipe> getAllRecipes(String groupToken) {
        return getAllRecipes(convertTokenToGroupId(groupToken));
    }

    @Transactional
    public void likeRecipe(int recipeId, int groupId) {
        GroupRecipesEntity groupRecipe = em.find(GroupRecipesEntity.class, recipeId);
        if (groupRecipe != null && groupRecipe.getGroup_id() == groupId) {
            groupRecipe.setLikes(groupRecipe.getLikes() + 1);
            em.merge(groupRecipe);
        }
    }

    @Transactional
    public void likeRecipe(int recipeId, String groupToken) {
        likeRecipe(recipeId, convertTokenToGroupId(groupToken));
    }

    private int convertTokenToGroupId(String groupToken) {
        return em.createQuery("SELECT g.id FROM GroupEntity g WHERE g.group_token = :token", Integer.class)
                .setParameter("token", groupToken)
                .getSingleResult();
    }
}
