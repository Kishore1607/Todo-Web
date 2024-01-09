package com.learning.todo.serviceImpl;

import com.learning.todo.entity.UserEntity;
import com.learning.todo.repository.UserRepositoryCustom;
import com.learning.todo.validation.UserValidation;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
//@Validated
//@Import(UserValidation.class)
public class UserServiceImpl implements UserRepositoryCustom {
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserValidation userValidation) {
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
    }

        private String generateRandomString(){
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public UserEntity loginUser(UserEntity user) {
        TypedQuery<UserEntity> query = null;

        try {
            query = entityManager.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.name = ?1 AND u.password = ?2", UserEntity.class);

            query.setParameter(1, user.getName());
            query.setParameter(2, passwordEncoder.encode(user.getPassword()));

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }


    @Transactional
    public UserEntity registerUser(UserEntity newUser) {

        if(!userValidation.uniqueName(newUser.getName())) {
            return null;
        }

        newUser.setEntryPass(generateRandomString());
        newUser.setName(passwordEncoder.encode(newUser.getName()));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        entityManager.createNativeQuery("INSERT INTO user_entity (name, password, entry_pass) VALUES (?,?,?)")
                .setParameter(1, newUser.getName())
                .setParameter(2, newUser.getPassword())
                .setParameter(3, newUser.getEntryPass())
                .executeUpdate();

        UserEntity responseEntity = new UserEntity();
        responseEntity.setName(newUser.getName());
        responseEntity.setEntryPass(newUser.getEntryPass());

        return responseEntity;
    }

}
