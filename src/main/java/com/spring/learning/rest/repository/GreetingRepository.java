package com.spring.learning.rest.repository;

import java.util.List;
import java.util.Optional;

import com.spring.learning.rest.dto.GreetingRequest;
import com.spring.learning.rest.dto.GreetingResponse;

public interface GreetingRepository {
    Optional<GreetingResponse> findById(Long id);
    List<GreetingResponse> findAll();
    boolean deleteById(Long id);
    Optional<GreetingResponse> update(Long id, GreetingRequest request);
    GreetingResponse create(GreetingRequest request);
}
