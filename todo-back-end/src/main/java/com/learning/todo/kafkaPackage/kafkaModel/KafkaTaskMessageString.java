package com.learning.todo.kafkaPackage.kafkaModel;

import com.learning.todo.entity.TasksEntity;
import org.springframework.stereotype.Component;

@Component
public class KafkaTaskMessageString {
    public String createTaskLogMessage(String actionName, TasksEntity tasksEntity, long userId) {
        return "{" +
                " actionName=' " + actionName +
                ", taskName= " + tasksEntity.getTaskName() +
                ", userId= " + userId +
                ", priority= " + tasksEntity.getPriority() +
                ", date='" + tasksEntity.getDate() + '\'' +
                '}';
    }

    public String updateTaskLogMessage(String actionName, long taskId, TasksEntity newTask, long userId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", taskId= " + taskId +
                " newTask: {" +
                " taskName= " + newTask.getTaskName() +
                ", priority= " + newTask.getPriority() +
                ", date='" + newTask.getDate() + '\'' +
                "}";
    }

    public String getTaskLogMessage(String actionName, long userId, long taskId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", taskId= " + taskId +
                '}';
    }

    public String statusTaskListLogMessage(String actionName, long userId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                '}';
    }

    public String statusSpecificTaskListLogMessage(String actionName, long userId, String status) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", requestedTaskList= " + status +
                '}';
    }

    public String statusTaskLogMessage(String actionName, long userId, Long taskId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", taskId= " + taskId +
                '}';
    }

}
