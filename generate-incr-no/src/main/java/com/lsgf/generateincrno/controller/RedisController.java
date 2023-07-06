package com.lsgf.generateincrno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class RedisController {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping("/crtIncrNo")
    public String generateIncrNo(String key) {
        Long incrNo = redisTemplate.opsForValue().increment(key);
        return ""+incrNo;
    }
}
