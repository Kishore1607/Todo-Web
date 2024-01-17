package com.learning.todo.serviceInterface;

import com.learning.todo.entity.TasksEntity;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface TaskServiceInterface {
    List<TasksEntity> getTasksList(Long id);
    List<TasksEntity> getSpecificTasksList(Long id, String status);
    void createTask(TasksEntity tasksEntity);
    boolean updateTask(TasksEntity tasksEntity);
    void deleteTask(Long  id);

    List<TasksEntity> getNotificationList(Long id);

}
