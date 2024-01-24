package com.learning.todo.kafkaPackage.kafkaModel;

import com.learning.todo.entity.TasksEntity;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageString {
    public String createTaskLogMessage(String actionName, TasksEntity tasksEntity, long userId, String status) {
        return "{" +
                " actionName=' " + actionName +
                ", taskName= " + tasksEntity.getTaskName() +
                ", userId= " + userId +
                ", status= " + status +
                ", priority= " + tasksEntity.getPriority() +
                ", date='" + tasksEntity.getDate() + '\'' +
                '}';
    }

    public String updateTaskLogMessage(String actionName, TasksEntity oldTask, TasksEntity newTask, long userId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", oldTask: {" +
                " taskName= " + oldTask.getTaskName() +
                ", status= " + oldTask.getStatus() +
                ", priority= " + oldTask.getPriority() +
                ", date='" + oldTask.getDate() + '\'' +
                "}," +
                " newTask: {" +
                " taskName= " + newTask.getTaskName() +
                ", priority= " + newTask.getPriority() +
                ", date='" + newTask.getDate() + '\'' +
                "}" +
                '}';
    }

    public String statusTaskLogMessage(String actionName, long userId,long taskId) {
        return "{" +
                " actionName=' " + actionName +
                ", userId= " + userId +
                ", taskId= " + taskId +
                '}';
    }

}
