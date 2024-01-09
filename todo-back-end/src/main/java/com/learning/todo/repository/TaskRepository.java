package com.learning.todo.repository;

import com.learning.todo.entity.TasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface TaskRepository extends JpaRepository<TasksEntity, Long> {
}
