package com.lsgf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

//This test Class contains all redis methods that I know
@SpringBootTest
public class RedisMethodTest {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void testIncrNo() {
        Long testIncrNo = redisTemplate.opsForValue().increment("testIncrNo");
        System.out.println(testIncrNo);
    }

    @Test
    void testRedisAbility() {
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

    @Test
    public void writeHash() {
        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);

        redisTemplate.opsForHash().putAll("thash", map);
    }

    public void testRange() {
        redisTemplate.opsForList().range("listOps:leftPush", 0, -1);
    }

}
