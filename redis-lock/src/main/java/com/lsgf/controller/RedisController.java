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

    //add lock
    @RequestMapping("/addLock")
    public RspBean addLock(String key) {
        RspBean res=new RspBean();
        if (redisTemplate.opsForValue().setIfAbsent(key, null, 600, TimeUnit.SECONDS)){
            res.rspCode=0;
            res.rspMsg="ok";
        }else {
            res.rspCode=11;
            //返回占用者名字
            //res.rspMsg= (String) redisTemplate.opsForValue().get(key);
            res.rspMsg="已被占用";
        }
        return res;
    }

    //del lock
    @RequestMapping("/delLock")
    public RspBean delLock(String key){
        RspBean res=new RspBean();
        if (redisTemplate.hasKey(key)){
            if (redisTemplate.delete(key)){
                res.rspCode=0;
                res.rspMsg="delete success";
            }else {
                res.rspCode=-2;
                res.rspMsg="delete fail";
            }
        }else {
            res.rspCode=-1;
            res.rspMsg="key absent";
        }
        return res;
    }


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
