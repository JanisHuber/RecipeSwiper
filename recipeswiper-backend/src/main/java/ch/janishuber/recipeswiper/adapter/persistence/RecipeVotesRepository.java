package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.RecipeVotesEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;
import ch.janishuber.recipeswiper.domain.VoteResult;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class RecipeVotesRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public void saveVote(int recipeId, int userId, int groupId, VoteType voteType) {
        if (hasUserAlreadyVoted(recipeId, userId, groupId)) {
            throw new IllegalStateException("User has already voted for this recipe");
        }
        RecipeVotesEntity recipeVote = new RecipeVotesEntity(recipeId, userId, groupId, voteType);
        em.persist(recipeVote);
    }

    @Transactional
    public void saveVote(int recipeId, String userToken, String groupToken, VoteType voteType) {
        saveVote(recipeId, convertTokenToUserId(userToken), convertTokenToGroupId(groupToken), voteType);
    }

    public List<VoteResult> getRecipeVoteFromGroup(int groupId, int recipeId) {
        try {
            List<RecipeVotesEntity> r = em.createQuery(
                    "SELECT r FROM RecipeVotesEntity r WHERE r.group_id = :groupId AND r.recipe_id = :recipeId",
                    RecipeVotesEntity.class)
                    .setParameter("groupId", groupId)
                    .setParameter("recipeId", recipeId)
                    .getResultList();
            return r.stream()
                    .map(vote -> new VoteResult(vote.getUser_id(), vote.getRecipe_id(), vote.getGroup_id(),
                            vote.getVote()))
                    .toList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<VoteResult> getRecipeVoteFromGroup(String groupToken, int recipeId) {
        return getRecipeVoteFromGroup(convertTokenToGroupId(groupToken), recipeId);
    }

    private boolean hasUserAlreadyVoted(int recipeId, int userId, int groupId) {
        return em.createQuery(
                "SELECT COUNT(v) FROM RecipeVotesEntity v " +
                        "WHERE v.recipe_id = :recipeId AND v.user_id = :userId AND v.group_id = :groupId",
                Long.class)
                .setParameter("recipeId", recipeId)
                .setParameter("userId", userId)
                .setParameter("groupId", groupId)
                .getSingleResult() > 0;
    }

    private int convertTokenToUserId(String userToken) {
        return em.createQuery("SELECT u.id FROM UserEntity u WHERE u.user_token = :token", Integer.class)
                .setParameter("token", userToken)
                .getSingleResult();
    }

    private int convertTokenToGroupId(String groupToken) {
        return em.createQuery("SELECT g.id FROM GroupEntity g WHERE g.group_token = :token", Integer.class)
                .setParameter("token", groupToken)
                .getSingleResult();
    }
}
