package com.isaac.serviceconsumer1.exception;

import com.isaac.serviceconsumer1.domain.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Ticket> handleFileNotFoundException() {
        return ResponseEntity.ok(new Ticket(23, "file not found"));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.ok("Exception occurs");
    }
}
