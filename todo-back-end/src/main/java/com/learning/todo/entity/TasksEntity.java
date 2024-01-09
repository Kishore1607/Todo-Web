package com.learning.todo.entity;

import com.learning.todo.enumPackage.Priority;
import com.learning.todo.enumPackage.TaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "task_entity")
public class TasksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String taskName;
    @Column(name="user_id")
    private long userId;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "date")
    private String date;

    public TasksEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TasksEntity(String taskName, long userId, TaskStatus statu, Priority priority, String date) {
        this.taskName = taskName;
        this.userId = userId;
        this.status = statu;
        this.priority = priority;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TasksEntity{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", priority=" + priority +
                ", date='" + date + '\'' +
                '}';
    }
}
