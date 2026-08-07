package com.spring.learning.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.learning.core.beans.GreetingService;

@RestController
@RequestMapping("api/v1/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService){
        this.greetingService = greetingService;
    }

    @GetMapping()
    public String hello() {
        return greetingService.greet();
    }
}