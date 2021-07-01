package com.isaac.serviceprovider1.controller;

import com.isaac.serviceprovider1.domain.User;
import com.isaac.serviceprovider1.service.CacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    CacheTestService cacheTestService; // key and val both Object

    @GetMapping("/cacheget")
    public User cacheGet() {
        return cacheTestService.getTest(2, "haha");
    }
    @GetMapping("/cacheupdate")
    public String cacheUpdate() {
        return cacheTestService.updateTest(2, "hihi");
    }
    @GetMapping("/cacheevict")
    public String cacheEvict() {
        return cacheTestService.evictTest(2, "hihi");
    }
    @GetMapping("/caching")
    public String caching() {
        return cacheTestService.cachingTest(2, "hihi");
    }


}
