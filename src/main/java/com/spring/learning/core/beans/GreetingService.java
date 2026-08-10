package com.spring.learning.core.beans;

import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private final List<GreetingResponse> greetings = new ArrayList<>();

    private long nextId = 1;

    public GreetingResponse create(GreetingRequest request) {

        GreetingResponse greeting = new GreetingResponse(
                nextId++,
                request.name(),
                request.message());

        greetings.add(greeting);

        return greeting;
    }

    public List<GreetingResponse> findAll() {
        return greetings;
    }

    public GreetingResponse findById(Long id) {

        return greetings.stream()
                .filter(greeting -> greeting.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean delete(Long id) {

        return greetings.removeIf(
                greeting -> greeting.id().equals(id));
    }

    public GreetingResponse update(
            Long id,
            GreetingRequest request) {

        for (int i = 0; i < greetings.size(); i++) {

            GreetingResponse existing = greetings.get(i);

            if (existing.id().equals(id)) {

                GreetingResponse updated = new GreetingResponse(
                        id,
                        request.name(),
                        request.message());

                greetings.set(i, updated);

                return updated;
            }
        }

        return null;
    }
}