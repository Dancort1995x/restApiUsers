package com.example.users.rest.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String resourceName) {
        super(String.format(resourceName));
    }
}
