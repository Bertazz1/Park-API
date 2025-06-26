package com.Bertazz1.demo_park_api.web.exception;

import com.Bertazz1.demo_park_api.exception.UsernameUniqueException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Validation error", result));
    }

    @ExceptionHandler(UsernameUniqueException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(RuntimeException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Validation error", result));
    }
}

