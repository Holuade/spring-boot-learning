package com.spring.learning;

import com.spring.learning.core.beans.NotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PracticalApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(PracticalApplication.class, args);

        NotificationService notificationService =
                context.getBean(NotificationService.class);

        notificationService.sendNotification();
    }
}