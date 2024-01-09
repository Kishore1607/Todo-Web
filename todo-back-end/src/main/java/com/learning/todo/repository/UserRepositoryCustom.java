package com.learning.todo.repository;

import com.learning.todo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositoryCustom{
    UserEntity loginUser(UserEntity user);

    UserEntity registerUser(UserEntity newUser);

}
