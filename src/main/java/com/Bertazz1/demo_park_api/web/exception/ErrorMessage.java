package com.Bertazz1.demo_park_api.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.stream.Collectors;

@Getter @Setter @ToString @NoArgsConstructor
public class ErrorMessage {

    private String path;

    private String method;

    private int status;

    private String statusMessage;

    private String message;

    private Map<String, String> erros;

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
        addErros(bindingResult);
    }

    private void addErros(BindingResult bindingResult) {
        this.erros = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));
    }

}
