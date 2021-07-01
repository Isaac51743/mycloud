package com.isaac.serviceconsumer1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/github")
public class GithubConfigController {

    @Value("${spring.datasource.url}")
    private String remoteConfig;

    @GetMapping("/getconfig")
    public ResponseEntity<String> getRemoteConfig() {
        return ResponseEntity.ok(remoteConfig);
    }
}
