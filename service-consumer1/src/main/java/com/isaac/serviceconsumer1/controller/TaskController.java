package com.isaac.serviceconsumer1.controller;

import com.isaac.serviceconsumer1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/async")
    public ResponseEntity<String> async() {
        Future<String> result = taskService.futureTask();
        CompletableFuture<String> completableResult = taskService.completableFutureTask();

        try {
            return ResponseEntity.ok(result.get() + "and " + completableResult.join());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
