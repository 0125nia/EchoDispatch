package com.nia.echoDispatch.handler.receiver.kafka;

import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author nia
 * @description Kafka 消费者
 * @Date 2024/5/21
 */

@Component
@Slf4j
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.KAFKA)
public class KafkaReceiver {


    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     *
     * @param consumerRecord 消费数据
     */
    @KafkaListener(topics = "#{'${EchoDispatch.mq.topic}'}")
    public void consume(ConsumerRecord<?, String> consumerRecord){
        Optional<String> message = Optional.ofNullable(consumerRecord.value());
        if (message.isPresent()){
            List<TaskInfo> taskInfos = JSON.parseArray(message.get(), TaskInfo.class);
            consumeService.consumeSendMsg(taskInfos);
        }else {
            log.error("消息消费错误：topic:{} ,taskInfo:{}",consumerRecord.topic(),consumerRecord.value());
        }
    }

}
