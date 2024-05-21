package com.nia.echoDispatch.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.handler.utils.GroupIdUtils;
import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author nia
 * @description Kafka 消费者
 * @Date 2024/5/21
 */

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.KAFKA)
public class KafkaReceiver {
    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     * @param consumerRecord 消费数据
     */
    @KafkaListener(topics = "#{'${EchoDispatch.mq.topic}'}",containerFactory = "filterContainerFactory")
    public void consume(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String groupId){
        Optional<String> message = Optional.ofNullable(consumerRecord.value());
        if (message.isPresent()){
            List<TaskInfo> taskInfos = JSON.parseArray(message.get(), TaskInfo.class);
            String messageGroupId = GroupIdUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfos.iterator()));
            if (Objects.equals(groupId,messageGroupId)){
                consumeService.consumeSendMsg(taskInfos);
            }
        }else {
            log.error("消息消费错误：topic:{} , groupId:{}, taskInfo:{}",consumerRecord.topic(),groupId,consumerRecord.value());
        }
    }

}
