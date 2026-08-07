package com.spring.learning.core.beans;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final GreetingService greetingService;

    public NotificationService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void sendNotification() {
        System.out.println(
                "Notification: " + greetingService.greet()
        );
    }
}