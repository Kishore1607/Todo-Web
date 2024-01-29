package com.learning.todo.serviceImpl;

import com.learning.todo.entity.UserEntity;
import com.learning.todo.repository.UserRepositoryCustom;
import com.learning.todo.validation.UserValidation;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.name = :name", UserEntity.class);
        query.setParameter("name", user.getName());

        List<UserEntity> resultList = query.getResultList();
        System.out.print(resultList.size());

        for (UserEntity storedUser : resultList) {
            if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
                UserEntity loginuser = new UserEntity();
                loginuser.setId(storedUser.getId());
                loginuser.setName(storedUser.getName());
                loginuser.setEntryPass(storedUser.getEntryPass());
                return loginuser;
            }
        }

        return null;
    }



    @Transactional
    public UserEntity registerUser(UserEntity newUser) {

        if(!userValidation.uniqueName(newUser.getName())) {
            return null;
        }

        newUser.setEntryPass(generateRandomString());
        newUser.setName(newUser.getName());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        entityManager.createNativeQuery("INSERT INTO user_entity (name, password, entry_pass) VALUES (?,?,?)")
                .setParameter(1, newUser.getName())
                .setParameter(2, newUser.getPassword())
                .setParameter(3, newUser.getEntryPass())
                .executeUpdate();

        UserEntity responseEntity = new UserEntity();
        responseEntity.setId(newUser.getId());
        responseEntity.setName(newUser.getName());
        responseEntity.setEntryPass(newUser.getEntryPass());

        return responseEntity;
    }

    @Override
    public long findUserByEntryPass(String name, String token) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.entryPass = :entryPass AND u.name = :name", UserEntity.class);
        query.setParameter("name", name);
        query.setParameter("entryPass", token);

        List<UserEntity> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            System.out.print(resultList.get(0).getId());
            return resultList.get(0).getId();
        } else {
            return -1;
        }
    }

}
