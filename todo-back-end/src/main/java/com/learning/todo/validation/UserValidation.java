package com.learning.todo.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserValidation {

    @PersistenceContext
    private EntityManager entityManager;

    public UserValidation(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public boolean uniqueName(@Value("${some.property}") String someProperty) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.name = ?1", Long.class);

        query.setParameter(1, someProperty);

        return query.getSingleResult() == 0;
    }

    public boolean allowEntry(@Value("${some.property}") String someProperty) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM UserEntity u WHERE u.entryPass = ?1", Long.class);

        query.setParameter(1, someProperty);

        return query.getSingleResult() > 0;
    }
}
