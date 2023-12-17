package com.example.users.rest.service.impl;

import com.example.users.rest.exception.ResourceNotFoundException;
import com.example.users.rest.exception.UserNotFoundException;
import com.example.users.rest.model.User;
import com.example.users.rest.repository.UserRepository;
import com.example.users.rest.service.UserService;
import com.example.users.rest.utils.JwtUtil;
import com.example.users.rest.utils.RegexUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RegexUtil regexUtil;


    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("usuario con uuid: " + userId);
        }
        return optionalUser.get();
    }


    public List<User> getAllUsers(){
        List<User> userList = userRepository.findAll();

        if(userList.isEmpty()){
            throw new UserNotFoundException("No hay registros de usuarios en el sistema");
        }

        return userList;
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Correo se encuentra registrado");
        }
        regexUtil.validRegexPattern(user.getEmail(), "email");
        regexUtil.validRegexPattern(user.getPassword(), "password");


        String token = jwtUtil.generateJwtToken(user.getEmail());
        user.setToken(token);
        return userRepository.save(user);

    }

    public User updateUser(String userId, User updatedUserData) {
        User existingUser = getUserById(userId);
        if (userRepository.findByEmail(updatedUserData.getEmail()).isPresent()
                && !Objects.equals(updatedUserData.getEmail(), existingUser.getEmail())) {
            throw new RuntimeException("Correo se encuentra registrado");
        }
        if (updatedUserData.getName() != null) {
            existingUser.setName(updatedUserData.getName());
        }
        if (updatedUserData.getEmail() != null) {
            regexUtil.validRegexPattern(updatedUserData.getEmail(), "email");
            existingUser.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getPassword() != null) {
            regexUtil.validRegexPattern(updatedUserData.getPassword(), "password");
            existingUser.setPassword(updatedUserData.getPassword());
        }
        existingUser.setPhones(updatedUserData.getPhones());
        userRepository.save(existingUser);

        return existingUser;
    }

    @Transactional
    public void deleteUserById(String userId) {
        getUserById(userId);
        userRepository.deleteUserById(userId);
    }


}
