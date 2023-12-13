package com.example.users.rest.service;

import com.example.users.rest.exception.ResourceNotFoundException;
import com.example.users.rest.model.User;
import com.example.users.rest.repository.UserRepository;
import com.example.users.rest.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public User getUserById(String userId){
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("usuario con uuid: "+userId);
        }
        return optionalUser.get();
    }

    public User createUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Correo se encuentra registrado");
        }

        String token = jwtUtil.generateJwtToken(user.getEmail());
        user.setToken(token);
        return userRepository.save(user);

    }
    public User updateUser(String userId, User updatedUserData) {
            User existingUser = getUserById(userId);
            if(userRepository.findByEmail(updatedUserData.getEmail()).isPresent()
            && !Objects.equals(updatedUserData.getEmail(), existingUser.getEmail())){
                throw new RuntimeException("Correo se encuentra registrado");
            }
            existingUser.setName(updatedUserData.getName());
            existingUser.setEmail(updatedUserData.getEmail());
            existingUser.setPassword(updatedUserData.getPassword());
            existingUser.setPhones(updatedUserData.getPhones());
            userRepository.save(existingUser);

            return existingUser;
    }

    @Transactional
    public void deleteUserById(String userId){
        getUserById(userId);
        userRepository.deleteUserById(userId);
    }



}
