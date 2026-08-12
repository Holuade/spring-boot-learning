package com.spring.learning.rest.dto;

public record ApiErrorResponse(
        int status,
        String message
) {
}
