package com.spring.learning.rest.dto;
import jakarta.validation.constraints.NotBlank;

public record GreetingRequest(
        @NotBlank
        String name,

        @NotBlank
        String message
) {
}