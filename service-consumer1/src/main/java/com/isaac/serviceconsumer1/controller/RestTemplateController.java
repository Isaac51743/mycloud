package com.isaac.serviceconsumer1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/resttemplate")
public class RestTemplateController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/getresponsebody")
    public ResponseEntity<String> getObject() {
        String result = restTemplate.getForObject("http://service-provider1/ticket/getticket", String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getresponse")
    public ResponseEntity<String> getEntity() {
        return restTemplate.getForEntity("http://service-provider1/ticket/getticket", String.class);
    }
}
