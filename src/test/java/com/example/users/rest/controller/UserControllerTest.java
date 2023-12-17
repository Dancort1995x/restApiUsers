package com.example.users.rest.controller;

import com.example.users.rest.model.Phone;
import com.example.users.rest.model.User;
import com.example.users.rest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    private User getMockUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("Name");
        user.setEmail("email@email.com");
        user.setPassword("Password123");

        List<Phone> getMockPhones = new ArrayList<>();
        Phone getphone = new Phone();

        getphone.setNumber("8888888");
        getphone.setCitycode("1");
        getphone.setContrycode("1");
        getMockPhones.add(getphone);
        user.setPhones(getMockPhones);

        return user;
    }


    @Test
    void createUserTest(){
        User mockUser = getMockUser();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(mockUser);
        ResponseEntity<User> response = userController.registerUser(mockUser);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockUser.getId(),response.getBody().getId());
    }


    @Test
    void getUserByIdTest(){
        User mockUser = getMockUser();
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(mockUser);
        ResponseEntity<User> response = userController.getUserDetails(mockUser.getId());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockUser.getId(),response.getBody().getId());
    }

    @Test
    void getAllUsers(){
        User mockUser = getMockUser();
        List<User> userList = new ArrayList<>();
        userList.add(mockUser);
        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<User>> response = userController.getAllUsers();
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userList.get(0).getId(),response.getBody().get(0).getId());
    }


    @Test
    void updateUserTest(){
        User mockUser = getMockUser();
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(mockUser);
        ResponseEntity<User> responseInit = userController.getUserDetails(mockUser.getId());
        User userUpdate = getMockUser();
        userUpdate.setEmail("emailupdate@email.com");
        Mockito.when(userService.updateUser(mockUser.getId(),mockUser)).thenReturn(userUpdate);
        ResponseEntity<User> responseUpdate = userController.updateUser(mockUser.getId(),mockUser);
        Assertions.assertNotNull(responseInit.getBody());
        Assertions.assertNotNull(responseUpdate.getBody());
        Assertions.assertNotEquals(responseInit.getBody().getEmail(),responseUpdate.getBody().getEmail());
        Assertions.assertEquals("emailupdate@email.com",responseUpdate.getBody().getEmail());
    }

    @Test
    void deleteUserTest(){
        User mockUser = getMockUser();
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(mockUser);
        userService.deleteUserById(mockUser.getId());
        Mockito.verify(userService).deleteUserById(Mockito.eq(mockUser.getId()));
    }

}
