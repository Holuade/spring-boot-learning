package com.spring.learning.rest.dto;

import java.util.Map;

public record ValidationResponse(

        int status,
        String message,
        Map<String, String> errors) {

}
