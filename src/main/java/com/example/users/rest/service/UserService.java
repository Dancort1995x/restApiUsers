package com.example.users.rest.service;

import com.example.users.rest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {



    User getUserById(String userId);

    List<User> getAllUsers();

    User createUser(User user);
    User updateUser (String userId, User updatedUserData);

    void deleteUserById(String id);

}
