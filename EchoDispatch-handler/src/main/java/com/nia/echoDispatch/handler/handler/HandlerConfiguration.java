package com.nia.echoDispatch.handler.handler;

import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.handler.handler.impl.EmailHandler;
import com.nia.echoDispatch.handler.handler.impl.SmsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nia
 * @description 配置Handler
 * @Date 2024/5/30
 */
@Configuration
public class HandlerConfiguration {

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    private SmsHandler smsHandler;


    @Bean("handlerMap")
    public Map<Integer,Handler> handlerMap(){
        Map<Integer,Handler> handlerMap = new HashMap<>();

        handlerMap.put(ChannelType.EMAIL.getCode(), emailHandler);
        handlerMap.put(ChannelType.SMS.getCode(), smsHandler);

        return handlerMap;
    }


}
