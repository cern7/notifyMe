package com.notifyme.application.repository;

import com.notifyme.application.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public void insertAll(Iterable<User> users) {
        Session session = entityManager.unwrap(Session.class);
        session.setJdbcBatchSize(100);
        for (User user : users) {
            entityManager.persist(user);
        }
    }
}
