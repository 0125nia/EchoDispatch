package com.nia.echoDispatch.handler.Deduplication;

import com.nia.echoDispatch.common.constant.BasicConstant;
import com.nia.echoDispatch.common.enums.TraceStatus;
import com.nia.echoDispatch.support.utils.LogUtil;
import com.nia.echoDispatch.support.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author nia
 * @description 使用redis实现去重
 * @Date 2024/5/23
 */

@Service
@Slf4j
public class DeduplicationService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${EchoDispatch.redis.key.deduplication}")
    private String HEAD;

    /**
     * 去重：检查5min内是否发送了重复的信息 以及 是否在一天内发送超5条信息
     *
     * @param code
     * @param receiver
     * @param msg
     * @return
     */
    public boolean deduplication(String code, String receiver, String msg) {
        // 生成key的前缀与完整的key
        String headKey = generateHeadKey(receiver);
        String key = generateTotalKey(receiver, code);
        // 判断是否存在这个键或是否发送消息频繁
        if (!redisUtil.hasKey(key) && judgeFrequent(headKey)) {
            // 没有查询到此键,加入到redis中
            redisUtil.put(key, msg, BasicConstant.MAX_SEND_A_DAY);
            return true;
        }
        //重复则返回false
        return !judgeDuplicate(msg, key);
    }

    /**
     * 判断是否频繁发送消息（一天内发送5条）
     *
     * @param key key
     * @return 是否频繁发送 频繁 则返回false
     */
    private boolean judgeFrequent(String key) {
        return redisUtil.countKeys(key) <= BasicConstant.MAX_MESSAGE_IN_SET_TIME;
    }

    /**
     * 判断是否在指定时间内发送了重复的消息
     *
     * @param msg 内容
     * @param key key
     * @return 是否重复，重复返回true
     */
    private boolean judgeDuplicate(String msg, String key) {
        String message = redisUtil.get(key);
        return Objects.equals(message, msg);
    }


    /**
     * 生成redis的key
     *
     * @param receiver 接收者
     * @param code     消息类型作为key的一部分
     * @return 生成的key
     */
    public String generateTotalKey(String receiver, String code) {
        return generateHeadKey(receiver) + "." + code;
    }

    /**
     * 生成key前缀
     *
     * @param receiver 接收者
     * @return 生成的key前缀
     */
    private String generateHeadKey(String receiver) {
        return HEAD + receiver;
    }
}
