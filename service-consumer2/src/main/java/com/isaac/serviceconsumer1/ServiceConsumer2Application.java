package com.isaac.serviceconsumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ServiceConsumer2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer2Application.class, args);
    }

}
