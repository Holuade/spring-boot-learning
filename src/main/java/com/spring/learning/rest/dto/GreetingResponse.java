package com.spring.learning.rest.dto;

public record GreetingResponse(
        Long id,
        String name,
        String message
) {
}