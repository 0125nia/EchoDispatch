package com.nia.echoDispatch.support.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description
 * @Date 2024/5/23
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean hasKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    public void put(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void put(String key,String value,Long time){
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }

    public void remove(String key){
        redisTemplate.delete(key);
    }




}
