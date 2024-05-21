package com.nia.echoDispatch.support.mq;

/**
 * @author nia
 * @description MQ发送消息接口
 * @Date 2024/5/21
 */
public interface MQService {
    void send(String topic, String messageJson);
}
