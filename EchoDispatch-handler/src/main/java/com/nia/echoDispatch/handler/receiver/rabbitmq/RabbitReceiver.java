package com.nia.echoDispatch.handler.receiver.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nia
 * @description Rabbit 消费者
 * @Date 2024/5/21
 */
@Component
@Slf4j
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.RABBIT_MQ)
public class RabbitReceiver {

    @Autowired
    private ConsumeService consumeService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${EchoDispatch.rabbitmq.queue}", durable = "true"),
            exchange = @Exchange(value = "${EchoDispatch.rabbitmq.exchange}", type = ExchangeTypes.TOPIC),
            key = "${EchoDispatch.rabbitmq.key}"
    ))
    public void consume(Message message) {
        String messageBody = new String(message.getBody());
        if (messageBody.isBlank()) {
            log.error("messageBody:{} is blank",messageBody);
            return;
        }
        List<TaskInfo> taskInfos = JSON.parseArray(messageBody, TaskInfo.class);
        consumeService.consumeSendMsg(taskInfos);
    }
}
