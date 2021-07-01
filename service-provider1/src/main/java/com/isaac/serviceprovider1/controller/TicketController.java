package com.isaac.serviceprovider1.controller;

import com.isaac.serviceprovider1.domain.Ticket;
import com.isaac.serviceprovider1.domain.User;
import com.isaac.serviceprovider1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/getticket")
    public Ticket getTicket() {
        return new Ticket(123, "my life");
    }

    @GetMapping("/getuser")
    public User getUser() {
        return userRepository.findById("a@a").get();
    }

    @GetMapping("/getuser/{password}")
    public List<User> getUserWithPassword(@PathVariable String password) {
        return userRepository.getUserSpecifyPassword(password);
    }

}
