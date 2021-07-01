package com.isaac.serviceconsumer1.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TaskService {

    @Async("threadPool1")
    public Future<String> futureTask() {
        try {
            Thread.sleep(3000);
            return new AsyncResult<String>("Async Future task complete!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Async("threadPool2")
    public CompletableFuture<String> completableFutureTask() {
        try {
            Thread.sleep(3000);
            return CompletableFuture.completedFuture("Async CompletableFuture task complete!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
