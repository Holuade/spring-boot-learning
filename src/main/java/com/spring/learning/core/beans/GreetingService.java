package com.spring.learning.core.beans;

import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;
import com.spring.learning.rest.exception.GreetingNotFoundException;
import com.spring.learning.rest.repository.GreetingRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public GreetingResponse create(GreetingRequest request) {
        return greetingRepository.create(request);
    }

    public List<GreetingResponse> findAll() {
        return greetingRepository.findAll();
    }

    public GreetingResponse findById(Long id) {
        return greetingRepository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException(id));
    }

    public void delete(Long id) {
        boolean deleted = greetingRepository.deleteById(id);

        if (!deleted) {
            throw new GreetingNotFoundException(id);
        }
    }

    public GreetingResponse update(Long id, GreetingRequest request) {
        return greetingRepository.update(id, request)
                .orElseThrow(() -> new GreetingNotFoundException(id));
    }

     public List<GreetingResponse> findByName(String name) {
        return greetingRepository.findByName(name);
                
    }
}