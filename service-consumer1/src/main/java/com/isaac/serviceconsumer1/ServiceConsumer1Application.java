package com.isaac.serviceconsumer1;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableRabbit
@EnableAsync
public class ServiceConsumer1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer1Application.class, args);
    }

}
