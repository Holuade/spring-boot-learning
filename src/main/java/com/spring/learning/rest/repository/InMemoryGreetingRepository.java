package com.spring.learning.rest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// import org.springframework.stereotype.Repository;

import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;

// @Repository
public class InMemoryGreetingRepository implements GreetingRepository {

    private final List<GreetingResponse> greetings = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Optional<GreetingResponse> findById(Long id) {
        return greetings.stream()
                .filter(greeting -> greeting.id().equals(id))
                .findFirst();
    }

    @Override
    public List<GreetingResponse> findAll() {
        return List.copyOf(greetings);
    }

    @Override
    public boolean deleteById(Long id) {
        return greetings.removeIf(greeting -> greeting.id().equals(id));
    }

    @Override
    public Optional<GreetingResponse> update(Long id, GreetingRequest request) {
        Optional<GreetingResponse> existingOpt = findById(id);

        if(existingOpt.isEmpty()) {
            return Optional.empty();
        }
        GreetingResponse existing = existingOpt.get();
        int index = greetings.indexOf(existing);

        GreetingResponse updated = new GreetingResponse(id, request.name(), request.message());
        greetings.set(index, updated);

        return Optional.of(updated);
    }

    @Override
    public GreetingResponse create(GreetingRequest request) {
        GreetingResponse greeting = new GreetingResponse(

                nextId++,
                request.name(),
                request.message());

        greetings.add(greeting);

        return greeting;
    }

    @Override
    public List<GreetingResponse> findByName(String name) {
        return greetings.stream()
        .filter(greeting -> greeting.name().equalsIgnoreCase(name))
        .toList();
    }

}
