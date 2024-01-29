package com.learning.todo.controller;

import com.learning.todo.entity.UserEntity;
import com.learning.todo.kafkaPackage.kafkaServices.KafkaProducerService;
import com.learning.todo.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepositoryCustom userRepositoryCustom;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public UserController(UserRepositoryCustom userRepositoryCustom, KafkaProducerService kafkaProducerService) {
        this.userRepositoryCustom = userRepositoryCustom;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user) {
        UserEntity newUser = userRepositoryCustom.registerUser(user);
        if (newUser != null) {

            // new user log info
            String regiLog = ("{action name: register,\n registered UserID: "+newUser.getId()+"}");
            kafkaProducerService.sendUserMessage("user", regiLog);

            System.out.print("came out");

            return ResponseEntity.ok(newUser);
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"User was not created\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserEntity user) {
        System.out.println(user.getName());
        UserEntity exist = userRepositoryCustom.loginUser(user);
        if (exist != null) {

            // exist user log info
            String loginLog = ("{action name: login, logined UserID: "+exist.getId()+"}");
            kafkaProducerService.sendUserMessage("user", loginLog);

            return ResponseEntity.ok(exist);
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"User does not exist\"}");
        }
    }
}
