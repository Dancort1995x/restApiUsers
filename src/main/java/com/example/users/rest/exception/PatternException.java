package com.example.users.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PatternException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(PatternException.class);

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
        log.error(resourceName + " no cumple con el formato valido");
        this.resourceName = resourceName;
    }
}
