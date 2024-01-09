package com.learning.todo.serviceImpl;

import com.learning.todo.entity.TasksEntity;
import com.learning.todo.enumPackage.TaskStatus;
import com.learning.todo.repository.TaskRepository;
import com.learning.todo.repository.UserRepositoryCustom;
import com.learning.todo.serviceInterface.TaskServiceInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskServiceInterface {
    private final TaskRepository taskRepository;
    private EntityManager entityManager;
    private final UserRepositoryCustom userRepositoryCustom;

    public TaskServiceImpl(TaskRepository taskRepository, EntityManager entityManager, UserRepositoryCustom userRepositoryCustom) {
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
        this.userRepositoryCustom = userRepositoryCustom;
    }
    @Override
    public List<TasksEntity> getTasksList(Long userId) {
        TypedQuery<TasksEntity> query = entityManager.createQuery(
                "SELECT t FROM TasksEntity t WHERE t.userId = :userId", TasksEntity.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }
    @Override
    public List<TasksEntity> getSpecificTasksList(Long userId, String num) {
        TypedQuery<TasksEntity> query = entityManager.createQuery(
                "SELECT t FROM TasksEntity t WHERE t.userId = :userId AND t.status = :status", TasksEntity.class);
        query.setParameter("userId", userId);
        query.setParameter("status", TaskStatus.fromString(num));

        return query.getResultList();
    }
    @Override
    public void createTask(TasksEntity tasksEntity) {
        tasksEntity.setStatus(TaskStatus.fromString("1"));
        taskRepository.save(tasksEntity);
    }
    @Override
    public boolean updateTask(TasksEntity tasksEntity) {
        if (tasksEntity.getId() == 0) {
            throw new RuntimeException("Task not found by the id " + tasksEntity.getId());
        }

        Optional<TasksEntity> existingTaskOptional = taskRepository.findById(tasksEntity.getId());
        if (existingTaskOptional.isPresent()) {
            TasksEntity existingTask = existingTaskOptional.get();

            existingTask.setTaskName(tasksEntity.getTaskName());
            existingTask.setDate(tasksEntity.getDate());
            existingTask.setPriority(tasksEntity.getPriority());

            taskRepository.save(existingTask);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
