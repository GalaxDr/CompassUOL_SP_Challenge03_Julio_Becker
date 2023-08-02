package br.com.compassuol.sp.challenge.apigateway.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        Date timestamp = new Date();
        String message = ex.getMessage();
        String path = exchange.getRequest().getPath().toString();
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, path);

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        Date timestamp = new Date();
        String message = ex.getMessage();
        String details = request.getDescription(false);
        ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details);

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}






