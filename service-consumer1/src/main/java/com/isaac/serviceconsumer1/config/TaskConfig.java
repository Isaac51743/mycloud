package com.isaac.serviceconsumer1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class TaskConfig {

    @Bean
    public Executor threadPool1() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean
    public Executor threadPool2() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("MyExecutor");
        executor.initialize();
        return executor;
    }
}
