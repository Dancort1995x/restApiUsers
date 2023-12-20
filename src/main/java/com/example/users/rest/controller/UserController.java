package com.example.users.rest.controller;

import com.example.users.rest.model.User;

import com.example.users.rest.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable String userId){
        log.info("call getUserDetails");
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("call getAllUsers");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        log.info("call registerUser");
        User registeredUser = userService.createUser(user);
        return ResponseEntity.ok()
                .body(registeredUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updateUserData) {
        log.info("call updateUser");
        User updatedUser = userService.updateUser(userId, updateUserData);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById (@PathVariable String userId){
        log.info("call deleteUserById");
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("Usuario elminado con exito");
    }


}
