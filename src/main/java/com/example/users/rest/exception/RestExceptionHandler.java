package com.example.users.rest.exception;

import com.example.users.rest.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlderMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                          WebRequest webRequest) {
        Map<String, String> mapErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
                    String clave = ((FieldError) error).getField();
                    String valor = error.getDefaultMessage();
                    mapErrors.put(clave, valor);
                }
        );
        ApiResponse apiResponse = new ApiResponse(mapErrors.toString(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception exception,
                                                        WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PatternException.class)
    public ResponseEntity<ApiResponse> handlerPatternException(PatternException exception,
                                                                WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handlerIlegalException(IllegalArgumentException exception,
                                                                WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerUsersNotFoundException(UsersNotFoundException exception,
                                                                WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiResponse> handlerGenericException(GenericException exception,
                                                                     WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}