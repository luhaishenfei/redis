package com.lsgf;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.boot.SpringApplication.run;


@SpringBootApplication
public class RedisLockApplication {
    public static void main(String[] args) {
        run(RedisLockApplication.class, args);
    }
}
