package com.spring.learning.rest.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.learning.core.beans.GreetingService;
import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public ResponseEntity<GreetingResponse> create(
            @Valid @RequestBody GreetingRequest request) {

        GreetingResponse response = greetingService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<GreetingResponse>> findAll() {

        return ResponseEntity.ok(
                greetingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GreetingResponse> findById(@PathVariable Long id) {

        GreetingResponse greeting = greetingService.findById(id);

        return ResponseEntity.ok(greeting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        greetingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GreetingResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody GreetingRequest request) {

        GreetingResponse updated = greetingService.update(id, request);

        return ResponseEntity.ok(updated);
    }
}