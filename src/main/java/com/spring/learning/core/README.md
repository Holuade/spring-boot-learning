# Spring Beans

## What is a Bean?

A Spring Bean is an object that is created and managed by the Spring IoC container.

## `@Component`

`@Component` tells Spring that a class should be discovered during component scanning and registered as a Spring-managed bean.

## IoC

Inversion of Control means that Spring takes responsibility for creating and managing application objects instead of the application manually creating and wiring every object.

## Dependency Injection

Dependency Injection is the process through which Spring supplies a bean's required dependencies.

## Constructor Injection

Constructor injection provides dependencies through a class constructor.

Example:

```java
@Component
public class NotificationService {

    private final GreetingService greetingService;

    public NotificationService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}

## `@Configuration` and `@Bean`

`@Configuration` identifies a class that contains explicit Spring configuration.

`@Bean` is placed on a method and tells Spring to manage the object returned by that method as a Spring Bean.

Example:

```java
@Configuration
public class AppConfig {

    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }
}