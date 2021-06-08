package com.example.User.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> badRequestHandleException(Exception e) {

        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(result);
    }

}

