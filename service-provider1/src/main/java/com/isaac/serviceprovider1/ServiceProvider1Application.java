package com.isaac.serviceprovider1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class ServiceProvider1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider1Application.class, args);
    }

}
