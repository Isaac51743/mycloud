package com.isaac.serviceconsumer1.service;

import com.isaac.serviceconsumer1.domain.Ticket;
import com.isaac.serviceconsumer1.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "SERVICE-PROVIDER1")
@RequestMapping("/ticket")
public interface TicketService {
    @GetMapping("/getticket")
    Ticket getTicket();

    @GetMapping("/getuser")
    User getUser();

    @GetMapping("/getuser/{password}")
    List<User> getUserWithPassword(@PathVariable String password);
}
