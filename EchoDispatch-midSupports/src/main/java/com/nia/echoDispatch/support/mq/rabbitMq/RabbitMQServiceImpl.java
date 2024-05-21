package com.nia.echoDispatch.support.mq.rabbitMq;

import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import com.nia.echoDispatch.support.mq.MQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author nia
 * @description rabbit MQ实现类
 * @Date 2024/5/21
 */
@Service
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.RABBIT_MQ)
public class RabbitMQServiceImpl implements MQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${EchoDispatch.rabbitmq.exchange}")
    private String exchange;

    @Override
    public void send(String topic, String messageJson) {
        rabbitTemplate.convertAndSend(exchange,topic,messageJson);
    }
}
