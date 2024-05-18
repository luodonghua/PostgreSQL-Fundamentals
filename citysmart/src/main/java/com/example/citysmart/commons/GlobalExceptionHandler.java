package com.example.citysmart.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        
        return new ResponseEntity<>(body, headers, status);
    }

    // Validation failure below related to this exception
    // {"type":"about:blank","title":"Bad Request","status":400,"detail":"Validation failure","instance":"/addemplyees"}
    // The customized message as below:
    // {"errors":["employees: Max age is 68","employees: Department name is required"]}

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, 
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new HashMap<>();

        List<String> errors = new ArrayList<>();

        for (final var validation : ex.getAllValidationResults()) {
            final String parameterName = validation.getMethodParameter().getParameterName();
            validation
                .getResolvableErrors()
                .forEach(
                    error -> {
                        errors.add(parameterName+": " + error.getDefaultMessage());  
                    });
            }

        body.put("errors", (Object) errors);

        return new ResponseEntity<>(body, headers, status);
    }


}
