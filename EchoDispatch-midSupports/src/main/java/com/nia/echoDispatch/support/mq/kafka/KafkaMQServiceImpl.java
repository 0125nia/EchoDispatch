package com.nia.echoDispatch.support.mq.kafka;

import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import com.nia.echoDispatch.support.mq.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author nia
 * @description kafka MQ实现类
 * @Date 2024/5/21
 */

@Service
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.KAFKA)
public class KafkaMQServiceImpl implements MQService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void send(String topic, String messageJson) {
        kafkaTemplate.send(topic, messageJson);
    }
}
