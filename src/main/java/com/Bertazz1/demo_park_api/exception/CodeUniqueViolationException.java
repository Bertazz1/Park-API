package com.Bertazz1.demo_park_api.exception;

public class CodeUniqueViolationException extends RuntimeException {
    public CodeUniqueViolationException(String s) {
        super(s);
    }
}
