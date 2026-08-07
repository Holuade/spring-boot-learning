package com.spring.learning.core.beans;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String greet() {
       return "I am learning Spring Core!";
    }
}