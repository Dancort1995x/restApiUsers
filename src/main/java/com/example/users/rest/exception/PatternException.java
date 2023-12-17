package com.example.users.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PatternException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public PatternException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no fue encontado con: %s='%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public PatternException(String resourceName ) {
        super(String.format("%s no cumple con el formato valido", resourceName));
        this.resourceName = resourceName;
    }
}
