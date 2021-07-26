package com.isaac.serviceconsumer1;

import com.isaac.serviceconsumer1.domain.Ticket;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ServiceConsumer1ApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void jwtTest() {
        String JWT = Jwts.builder()
                // add "authorities": "ROLE_ADMIN,AUTH_WRITE" in the payload
                .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
                // add "sub": "Isaac" in the payload
                .setSubject("Isaac")
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + 432_000_000))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, "P@ssw02d")
                .compact();
        System.out.println(JWT);
    }

    @Test
    void rabbitMQSendTest() {

        rabbitTemplate.convertAndSend("my topic exchange 1", "c.d", new Ticket(4, "hihi"));
    }

    @Test
    void rabbitMQReceiveTest() {
        Object o = rabbitTemplate.receiveAndConvert("my queue 2");
        System.out.println((Ticket)o);
    }

}
