package com.isaac.serviceconsumer1.controller;

import com.isaac.serviceconsumer1.domain.Ticket;
import com.isaac.serviceconsumer1.domain.User;
import com.isaac.serviceconsumer1.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/get")
    public ResponseEntity<Ticket> getTicket() {
        Ticket result = ticketService.getTicket();
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getuser1")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(ticketService.getUser());
    }

    @GetMapping("/getuser1/{password}")
    public ResponseEntity<List<User>> getUserWithPassword(@PathVariable String password) {
        return ResponseEntity.ok(ticketService.getUserWithPassword(password));
    }
}
