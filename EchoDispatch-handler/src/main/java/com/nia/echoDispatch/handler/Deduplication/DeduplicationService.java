package com.nia.echoDispatch.handler.Deduplication;

import com.nia.echoDispatch.support.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nia
 * @description 使用redis实现去重
 * @Date 2024/5/23
 */

@Service
@Slf4j
public class DeduplicationService {

    @Autowired
    private RedisUtils redisUtils;

    public static final String HEAD = "deduplication.";

    /**
     * 去重：检查5min内是否发送了重复的信息
     * @param groupId
     * @param receiver
     * @return
     */
    public boolean deduplication(String groupId,String receiver,String msg){
        String key = generateKey(receiver,groupId);
        if (redisUtils.hasKey(key)) {

            return false;
        }
        return true;





    }


    public String generateKey(String receiver,String groupId){
        return HEAD + receiver + "." + groupId;
    }


}
