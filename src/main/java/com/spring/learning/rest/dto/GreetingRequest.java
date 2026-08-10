package com.spring.learning.rest.dto;

public record GreetingRequest(
        String name,
        String message
) {
}