package com.example.users.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(GenericException.class);

    public GenericException(String resourceName) {
        super(resourceName);
        log.error(resourceName);
    }
}
