package com.spring.learning.rest.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.learning.rest.dto.ApiErrorResponse;
import com.spring.learning.rest.dto.ValidationResponse;

import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        Map<String, String> validationErrors = errors.stream()
                .map(error -> Map.entry(
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));

        ValidationResponse response = new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                validationErrors);

        return ResponseEntity.badRequest().body(response);
    }

        @ExceptionHandler(GreetingNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleGreetingNotFoundException(
                        GreetingNotFoundException ex) {

                ApiErrorResponse response = new ApiErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage()
                );

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }

}
