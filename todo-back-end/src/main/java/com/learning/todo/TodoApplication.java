package com.learning.todo;

import com.learning.todo.serviceImpl.TaskServiceImpl;
import com.learning.todo.serviceImpl.UserServiceImpl;
import com.learning.todo.serviceInterface.TaskServiceInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableScheduling
@SpringBootApplication
@EnableWebSecurity
public class TodoApplication {

	@Autowired
	private UserServiceImpl userServiceImpl;

	public static void main(String[] args) {

		SpringApplication.run(TodoApplication.class, args);
	}

	@Autowired
	private TaskServiceImpl taskServiceImpl;

	@PostConstruct
	public void init() {
		taskServiceImpl.updateStatusBasedOnDate();
	}

}
