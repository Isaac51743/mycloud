package com.isaac.serviceconsumer1.service;

import com.isaac.serviceconsumer1.domain.Ticket;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @RabbitListener(queues = {"my queue 1 "})
    public void receive1(Ticket ticket) {
        System.out.println("message received " + ticket);
    }

    @RabbitListener(queues = {"my queue 2"})
    public void receive2(Message message, Ticket ticket) {
        System.out.println("message received " + ticket);
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }

}
