package com.learning.todo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity", uniqueConstraints = @UniqueConstraint(name = "entry_pass", columnNames = {"entry_pass"}))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, length = 100)
    private String name;
    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="entry_pass", nullable = false, length = 100)
    private String entryPass;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = String.valueOf(name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEntryPass() {
        return entryPass;
    }

    public void setEntryPass(String entryPass) {
        this.entryPass = entryPass;
    }

    public UserEntity() {

    }
    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
