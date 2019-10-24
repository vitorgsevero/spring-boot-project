package io.vitorgsevero.project.handler;

import io.vitorgsevero.project.error.ResourceNotFoundDetails;
import io.vitorgsevero.project.error.ResourceNotFoundException;
import io.vitorgsevero.project.error.ValidationErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfException){
        ResourceNotFoundDetails rfnDetails = ResourceNotFoundDetails.Builder
                            .newBuilder()
                            .timestamp(new Date().getTime())
                            .status(HttpStatus.NOT_FOUND.value())
                            .title("Resource not found")
                            .detail(rnfException.getMessage())
                            .developerMessage(rnfException.getClass().getName())
                            .build();
                return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException){
        System.out.println(manvException.getBindingResult().getFieldError());
        ValidationErrorDetails rfnDetails = ValidationErrorDetails.Builder
                            .newBuilder()
                            .timestamp(new Date().getTime())
                            .status(HttpStatus.NOT_FOUND.value())
                            .title("Resource not found")
                            .detail(manvException.getMessage())
                            .developerMessage(manvException.getClass().getName())
                            .build();
                return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }
}
