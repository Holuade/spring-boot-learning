package com.spring.learning.rest.exception;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.learning.rest.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(GreetingNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleGreetingNotFound(
                        GreetingNotFoundException ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = new ApiErrorResponse(
                                Instant.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ex.getMessage(),
                                request.getRequestURI(),
                                Map.of());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> validationErrors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .collect(Collectors.toMap(
                                                error -> error.getField(),
                                                error -> error.getDefaultMessage()));

                ApiErrorResponse response = new ApiErrorResponse(
                                Instant.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                 HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Validation failed",
                                request.getRequestURI(),
                                validationErrors);

                return ResponseEntity
                                .badRequest()
                                .body(response);
        }
}