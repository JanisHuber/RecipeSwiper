package ch.janishuber.recipeswiper.adapter.persistence;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.UserEntity;
import ch.janishuber.recipeswiper.domain.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;

@RequestScoped
public class UserRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    @Transactional
    public int save(User user) {
        UserEntity userEntity = new UserEntity(user.username(), user.userToken());
        em.persist(userEntity);
        em.flush();
        return userEntity.getId();
    }

    public Optional<User> getUser(int userId) {
        UserEntity entity = em.find(UserEntity.class, userId);
        if (entity == null) {
            return Optional.empty();
        }
        User user = new User(entity.getId(), entity.getUsername(), entity.getUser_token());
        return Optional.of(user);
    }

    public UserEntity findUserByToken(String token) {
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.user_token = :token", UserEntity.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
