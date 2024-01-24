package com.learning.todo.controller;

import com.learning.todo.entity.TasksEntity;
import com.learning.todo.enumPackage.TaskStatus;
import com.learning.todo.kafkaPackage.kafkaModel.KafkaMessageString;
import com.learning.todo.kafkaPackage.kafkaServices.KafkaProducerService;
import com.learning.todo.repository.TaskRepository;
import com.learning.todo.repository.UserRepositoryCustom;
import com.learning.todo.serviceInterface.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class TaskController {
    private final TaskServiceInterface taskServiceInterface;
    private final UserRepositoryCustom userRepositoryCustom;
    private final TaskRepository taskRepository;
    private final KafkaProducerService kafkaProducerService;
//
    @Autowired
    private final KafkaMessageString kafkaMessageString;

    public TaskController(TaskServiceInterface taskServiceInterface, UserRepositoryCustom userRepositoryCustom, TaskRepository taskRepository, KafkaProducerService kafkaProducerService, KafkaMessageString kafkaMessageString ) {
        this.taskServiceInterface = taskServiceInterface;
        this.userRepositoryCustom = userRepositoryCustom;
        this.taskRepository = taskRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaMessageString = kafkaMessageString;
    }

    private long authenticateUser(String authorizationHeader) {
        String[] auth = authorizationHeader.split("\\$");
        String token = auth[0].substring("Bearer ".length());
        return userRepositoryCustom.findUserByEntryPass(auth[1], token);
    }

    @GetMapping("/tasks/{name}")
    public ResponseEntity<List<TasksEntity>> getTaskList(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String name) {
        String token = authorizationHeader.substring("Bearer ".length());

        long userId = userRepositoryCustom.findUserByEntryPass(name, token);

        if (userId == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TasksEntity> tasks = taskServiceInterface.getTasksList(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/notification/{name}")
    public ResponseEntity<List<TasksEntity>> getNotificationList(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String name) {
        String token = authorizationHeader.substring("Bearer ".length());

        long userId = userRepositoryCustom.findUserByEntryPass(name, token);

        if (userId == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TasksEntity> tasks = taskServiceInterface.getNotificationList(userId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/tasks/{name}/{status}")
    public ResponseEntity<List<TasksEntity>> getSpecificTaskList(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String name,
            @PathVariable String status) {

        String token = authorizationHeader.substring("Bearer ".length());
        long userId = userRepositoryCustom.findUserByEntryPass(name, token);

        if (userId == -1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TasksEntity> tasks = taskServiceInterface.getSpecificTasksList(userId, status);
        return ResponseEntity.ok(tasks);
    }
    @PostMapping("/addtask")
    public ResponseEntity<String> saveTask(@RequestHeader("Authorization") String authorizationHeader, @RequestBody TasksEntity tasksEntity) {
        long userId = authenticateUser(authorizationHeader);

        // Creating the new task
        tasksEntity.setUserId(userId);
        tasksEntity.setStatus(TaskStatus.Ongoing);
        taskServiceInterface.createTask(tasksEntity);

        // Log for crating task after
        String createLogMessageBefore = kafkaMessageString.createTaskLogMessage("Create-task", tasksEntity, userId, "completed");
        kafkaProducerService.sendMessage("my-kafka-topic", createLogMessageBefore);


        return ResponseEntity.ok("{\"message\": \"Task created successfully\"}");
    }
    @PatchMapping("taskComplete/{id}")
    public ResponseEntity<String> setComplete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        long userId = authenticateUser(authorizationHeader);
        if(userId < 1){
            return ResponseEntity.badRequest().body("{\"message\": \"User not found\"}");
        }
        try {
            Optional<TasksEntity> tasksEntityOptional = taskRepository.findById(id);

            if (tasksEntityOptional.isPresent()) {
                TasksEntity tasksEntity = tasksEntityOptional.get();
                tasksEntity.setStatus(TaskStatus.Completed);
                taskRepository.save(tasksEntity);
                return ResponseEntity.ok("{\"message\": \"Task updated successfully\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"error\": \"Task not found\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error updating task\"}");
        }
    }
    @PutMapping("/edittask")
    public ResponseEntity<String> updateTask(@RequestHeader("Authorization") String authorizationHeader, @RequestBody TasksEntity tasksEntity) {
        long userId = authenticateUser(authorizationHeader);
        if(userId < 1){
            return ResponseEntity.badRequest().body("{\"message\": \"User not found\"}");
        }
        boolean isEdit = taskServiceInterface.updateTask(tasksEntity);
        if(isEdit){
            return ResponseEntity.ok("{\"message\": \"Task updated successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Task not found\"}");
        }
    }
    @DeleteMapping("/deletetask/{id}")
    public ResponseEntity<String> deleteTask(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        long userId = authenticateUser(authorizationHeader);
        if(userId < 1){
            return ResponseEntity.badRequest().body("{\"message\": \"User not found\"}");
        }
        taskServiceInterface.deleteTask(id);
        return  ResponseEntity.ok("{\"message\": \"Task deleted successfully\"}");
    }
    @GetMapping("task/{id}")
    public ResponseEntity<?> getTask(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        long userId = authenticateUser(authorizationHeader);

        if (userId < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User not found\"}");
        }

        Optional<TasksEntity> tasksEntityOptional = taskRepository.findById(id);

        if (tasksEntityOptional.isPresent()) {
            return ResponseEntity.ok(tasksEntityOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Task not found\"}");
        }
    }

}
