package com.lsgf.controller;

import com.lsgf.pojo.RspBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.concurrent.TimeUnit;


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
