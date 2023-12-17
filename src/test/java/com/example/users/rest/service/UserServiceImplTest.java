package com.example.users.rest.service;


import com.example.users.rest.exception.GenericException;
import com.example.users.rest.exception.ResourceNotFoundException;
import com.example.users.rest.exception.UsersNotFoundException;
import com.example.users.rest.model.Phone;
import com.example.users.rest.model.User;
import com.example.users.rest.repository.UserRepository;
import com.example.users.rest.service.impl.UserServiceImpl;
import com.example.users.rest.utils.JwtUtil;
import com.example.users.rest.utils.RegexUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RegexUtil regexUtil;

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
        Phone getphone = getPhoneMock();
        getMockPhones.add(getphone);
        user.setPhones(getMockPhones);
        return user;
    }

    private static Phone getPhoneMock() {
        Phone getphone = new Phone();

        getphone.setNumber("8888888");
        getphone.setCitycode("1");
        getphone.setContrycode("1");
        return getphone;
    }


    @Test
    void createUserTest(){
        User userMock = getMockUser();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(jwtUtil.generateJwtToken(Mockito.anyString())).thenReturn("tokenMock");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userMock);
        Mockito.doNothing().when(regexUtil).validRegexPattern(Mockito.anyString(),Mockito.anyString());
        User response = userServiceImpl.createUser(userMock);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userMock.getId(),response.getId());
        Assertions.assertEquals("tokenMock",response.getToken());

    }

    @Test
    void createUserFoundTest(){
        User userMock = getMockUser();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(userMock));
        Assertions.assertThrows(GenericException.class,()-> userServiceImpl.createUser(userMock));
    }

    @Test
    void getUserByIdTest(){
        Optional<User> userOp = Optional.of(getMockUser());
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(userOp);
        User response = userServiceImpl.getUserById(userOp.get().getId());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userOp.get().getId(),response.getId());
    }

    @Test
    void getUserByIdNotFoundTest(){
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,()-> userServiceImpl.getUserById("1"));
    }

    @Test
    void getAllUsers(){
        User mockUser = getMockUser();
        List<User> userList = new ArrayList<>();
        userList.add(mockUser);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> response = userServiceImpl.getAllUsers();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userList.get(0).getId(),response.get(0).getId());
    }

    @Test
    void getAllUsersNotFound(){
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThrows(UsersNotFoundException.class,()-> userServiceImpl.getAllUsers());
    }

    @Test
    void updateUserTest(){
        User userMock = getMockUser();
        User userMock2 = getMockUser();
        Phone phoneMock = getPhoneMock();
        Mockito.when(userRepository.findUserById(userMock.getId())).thenReturn(Optional.of(userMock));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.doNothing().when(regexUtil).validRegexPattern(Mockito.anyString(),Mockito.anyString());
        User userResponseInit = userServiceImpl.getUserById(userMock.getId());
        Assertions.assertNotNull(userResponseInit);
        Assertions.assertEquals(userMock.getPhones(),userResponseInit.getPhones());
        phoneMock.setNumber("1");
        List<Phone> updatePhone = new ArrayList<>();
        updatePhone.add(phoneMock);
        userMock2.setPhones(updatePhone);
        Mockito.when(userRepository.findUserById(userMock2.getId())).thenReturn(Optional.of(userMock2));
        User responseUpdate = userServiceImpl.updateUser(userMock2.getId(),userMock2);
        Assertions.assertNotNull(responseUpdate);
        Assertions.assertNotEquals(userResponseInit.getPhones(),responseUpdate.getPhones());
    }

    @Test
    void deleteUserTest(){
        User userMock = getMockUser();
        Mockito.when(userRepository.findUserById(Mockito.anyString())).thenReturn(Optional.of(userMock));
        userServiceImpl.deleteUserById(userMock.getId());
        Mockito.verify(userRepository).deleteUserById(Mockito.eq(userMock.getId()));

    }

}
