package com.isaac.serviceprovider1.service;

import com.isaac.serviceprovider1.domain.Ticket;
import com.isaac.serviceprovider1.domain.User;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
@CacheConfig(cacheNames = {"my test"})
public class CacheTestService {

    @Cacheable(cacheNames = {"my test"}, key = "#arg1", cacheManager = "redisCacheManager", condition = "#arg1 > 1", sync = true)
    public User getTest(int arg1, String arg2) {
        System.out.println("cache getting test: " + arg1 + " with key " + arg2);
//        return new Ticket(100, "movie1");
        return new User();
    }

    @CachePut(cacheNames = {"my test"}, key = "#arg1", condition = "#arg1 > 1")
    public String updateTest(int arg1, String arg2) {
        System.out.println("cache updating test: " + arg1 + " with key " + arg2);
        return arg2;
    }

    @CacheEvict(/*cacheNames = {"my test"},*/ key = "#arg1", condition = "#arg1 > 1", beforeInvocation = true)
    public String evictTest(int arg1, String arg2) {
        System.out.println("cache evicting test: " + arg1 + " with key " + arg2);
        return arg2;
    }
    @Caching(
            cacheable = {
                    @Cacheable(cacheNames = {"my test"}, key = "#arg1", condition = "#arg1 > 1"),
                    @Cacheable(cacheNames = {"your test"}, key = "#arg1", condition = "#arg1 > 1")
            },
            put = {
                    @CachePut(cacheNames = {"my test"}, key = "#arg1", condition = "#arg1 > 1"),
                    @CachePut(cacheNames = {"your test"}, key = "#arg1", condition = "#arg1 > 1")
            },
            evict = {
                    @CacheEvict(cacheNames = {"your test"}, key = "#arg1", condition = "#arg1 > 1"),
                    @CacheEvict(cacheNames = {"your test"}, key = "#arg1", condition = "#arg1 > 1")
            }
    )
    public String cachingTest(int arg1, String arg2) {
        System.out.println("caching test: " + arg1 + " with key " + arg2);
        return arg2;
    }
}
