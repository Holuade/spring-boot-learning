package com.spring.learning.rest.exception;

public class GreetingNotFoundException extends RuntimeException {

    public GreetingNotFoundException(Long id) {
        super("Greeting with id " + id + " was not found");
    }
}