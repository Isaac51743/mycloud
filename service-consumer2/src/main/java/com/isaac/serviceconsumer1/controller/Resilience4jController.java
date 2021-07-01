package com.isaac.serviceconsumer1.controller;

import com.isaac.serviceconsumer1.domain.Ticket;
import com.isaac.serviceconsumer1.domain.User;
import com.isaac.serviceconsumer1.service.TicketService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/resilience")
public class Resilience4jController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/get")
    @RateLimiter(name="backendB",fallbackMethod = "rateLimiter")
    @CircuitBreaker(name="backendA",fallbackMethod = "circuitBreaker")
    public ResponseEntity<Ticket> getTicket() {
        Ticket result = ticketService.getTicket();
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getuser1")
    @RateLimiter(name="backendB",fallbackMethod = "rateLimiter")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(ticketService.getUser());
    }

    @GetMapping("/getuser1/{password}")
    @Retry(name="backendC",fallbackMethod = "retry")
    public ResponseEntity<List<User>> getUserWithPassword(@PathVariable String password) {
        return ResponseEntity.ok(ticketService.getUserWithPassword(password));
    }

    // fallback method must in the same class
    public ResponseEntity<String> circuitBreaker(Throwable t){

        return ResponseEntity.ok("circuit breaker open");
    }

    public ResponseEntity<String> rateLimiter(Throwable t){

        return ResponseEntity.ok("rate limit!");
    }

    public ResponseEntity<String> retry(Throwable t){

        return ResponseEntity.ok("wait for retry");
    }
}
