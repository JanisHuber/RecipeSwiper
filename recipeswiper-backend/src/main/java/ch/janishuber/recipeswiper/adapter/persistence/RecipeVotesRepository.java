package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.RecipeVotesEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class RecipeVotesRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    @Transactional
    public void saveVote(int recipeId, int userId, VoteType voteType) {
        if (hasUserAlreadyVoted(recipeId, userId)) {
            throw new IllegalStateException("User has already voted for this recipe");
        }
        RecipeVotesEntity recipeVote = new RecipeVotesEntity(recipeId, userId, voteType);
        em.persist(recipeVote);
        em.flush();
    }

    private boolean hasUserAlreadyVoted(int recipeId, int userId) {
        return em.createQuery(
                "SELECT COUNT(v) FROM RecipeVotesEntity v WHERE v.recipe_id = :recipeId AND v.user_id = :userId",
                Long.class)
                .setParameter("recipeId", recipeId)
                .setParameter("userId", userId)
                .getSingleResult() > 0;
    }
}
