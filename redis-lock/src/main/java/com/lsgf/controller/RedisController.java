package com.lsgf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping("addLink")
    //public boolean addLink(String userId, URL url)
    public boolean addLink(String userId, String url) {
        //redisTemplate.opsForList().leftPush(userId, url);
        redisTemplate.boundListOps(userId).leftPush(url);
        return true;
    }


    //set
    @RequestMapping("setKey")
    public boolean insertRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    @RequestMapping("setLockKey")
    public boolean setLockKey(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, 40, TimeUnit.SECONDS);
    }

    //get
    @GetMapping("getValue")
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
