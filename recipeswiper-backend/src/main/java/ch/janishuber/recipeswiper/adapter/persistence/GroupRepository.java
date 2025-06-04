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

import java.util.List;
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

    public Optional<Group> getGroupFromUser(int userId) {
        try {
            GroupEntity group = em.createQuery("SELECT g FROM GroupEntity g JOIN UserToGroupEntity utg ON g.id = utg.group_id WHERE utg.user_id = :userId", GroupEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(new Group(group.getId(), group.getGroup_token()));
        } catch (NoResultException e) {
            return Optional.empty();
        }
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

    public List<Group> getAllGroupsForUser(int userId) {
        return em.createQuery("""
                SELECT g FROM GroupEntity g
                JOIN UserToGroupEntity utg ON g.id = utg.group_id
                WHERE utg.user_id = :userId
                """, GroupEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(g -> new Group(g.getId(), g.getGroup_token()))
                .toList();
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
