package com.lsgf;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisLockApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

//    @Test
    public void testRange(){
       redisTemplate.opsForList().range("listOps:leftPush",0,-1);
    }


    @Test
    void testRedis(){
        int i = 0;
        try {
            long start = System.currentTimeMillis();// 开始毫秒数
            while (true) {
                long end = System.currentTimeMillis();
                if (end - start >= 1000) {// 当大于等于1000毫秒（相当于1秒）时，结束操作
                    break;
                }
                i++;
                redisTemplate.opsForValue().set("test" + i, i + "");
            }
        } finally {// 关闭连接
        }
        // 打印1秒内对Redis的操作次数
        System.out.println("redis每秒操作：" + i + "次");
    }

}
