package com.nia.echoDispatch.support.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Throwables;
import com.nia.echoDispatch.common.constant.BasicConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description redis工具类
 * @Date 2024/5/23
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查看是否存在key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    /**
     * 设置键值对
     * @param key
     * @param value
     */
    public void put(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获取key对应的值
     * @param key
     * @return
     */
    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置指定过期时间的键值对对象
     * @param key
     * @param value
     * @param time
     */
    public void put(String key,String value,Long time){
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.HOURS);
    }

    /**
     * 移除key
     * @param key
     */
    public void remove(String key){
        redisTemplate.delete(key);
    }


    /**
     * 根据key前缀查询key的数量
     * @param pattern 前缀
     * @return 数量
     */
    public Integer countKeys(String pattern){
        Set<String> keys = redisTemplate.keys(pattern + "*");
        if (CollUtil.isEmpty(keys)){
            return -1;
        }
        return keys.size();

    }

    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流 key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript
     * @param keys
     * @param args
     * @return
     */
    public Boolean execLimitLua(RedisScript<Long> redisScript, List<String> keys, String... args) {

        try {
            Long execute = redisTemplate.execute(redisScript, keys, args);
            if (Objects.isNull(execute)) {
                return false;
            }
            return BasicConstant.TRUE.equals(execute.intValue());
        } catch (Exception e) {

            log.error("redis execLimitLua fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return false;
    }

}
