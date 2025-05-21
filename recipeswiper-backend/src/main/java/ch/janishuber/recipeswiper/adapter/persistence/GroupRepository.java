package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.GroupEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.UserEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.UserToGroupEntity;
import ch.janishuber.recipeswiper.domain.Group;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;

@RequestScoped
public class GroupRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    @Transactional
    public int save(String token) {
        GroupEntity groupEntity = new GroupEntity(token);
        em.persist(groupEntity);
        em.flush();
        return groupEntity.getId();
    }

    public Optional<Group> getGroupToken(int groupId) {
        GroupEntity entity = em.find(GroupEntity.class, groupId);
        if (entity == null) {
            return Optional.empty();
        }
        Group group = new Group(groupId, entity.getGroup_token());
        return Optional.of(group);
    }

    @Transactional
    public boolean joinGroup(int groupId, int userId) {
        GroupEntity group = em.find(GroupEntity.class, groupId);
        UserEntity user = em.find(UserEntity.class, userId);
        if (group == null || user == null || isUserAlreadyInGroup(userId, groupId)) {
            return false;
        }

        UserToGroupEntity userToGroupEntity = new UserToGroupEntity(user.getId(), group.getId());
        em.persist(userToGroupEntity);
        em.flush();
        return true;
    }

    public GroupEntity findGroupByToken(String token) {
        try {
            return em.createQuery("SELECT g FROM GroupEntity g WHERE g.group_token = :token", GroupEntity.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private boolean isUserAlreadyInGroup(int userId, int groupId) {
        Long count = em.createQuery("""
                SELECT COUNT(utg) FROM UserToGroupEntity utg
                WHERE utg.user_id = :userId AND utg.group_id = :groupId
                """, Long.class)
                .setParameter("userId", userId)
                .setParameter("groupId", groupId)
                .getSingleResult();
        return count > 0;
    }
}
