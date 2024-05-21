package com.nia.echoDispatch.handler.receiver.kafka;

import cn.hutool.core.text.StrPool;
import com.nia.echoDispatch.handler.utils.GroupIdUtils;
import com.nia.echoDispatch.support.constants.MQPipelineConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author nia
 * @description kafka配置
 * @Date 2024/5/21
 */
@Configuration
@ConditionalOnProperty(name = "EchoDispatch.mq.pipeline", havingValue = MQPipelineConstants.KAFKA)
public class KafkaConfig {


    /**
     * 获取所有groupId
     */
    private static List<String> groupIds = GroupIdUtils.getAllGroupIds();

    @Autowired
    private ApplicationContext context;
    /**
     * 下标(用于迭代groupIds位置)
     */
    private static Integer index = 0;

    /**
     * receiver的消费方法常量
     */
    private static final String RECEIVER_METHOD_NAME = "Receiver.consumer";

    /**
     * 为每一个渠道不同的消息类型，创建一个KafkaReceiver对象
     */
    @PostConstruct
    public void init(){
        for (int i = 0; i < groupIds.size(); i++) {
            context.getBean(KafkaReceiver.class);
        }
    }

    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return (attrs, element) -> {
            if (element instanceof Method) {
                String name = ((Method) element).getDeclaringClass().getSimpleName() + StrPool.DOT + ((Method) element).getName();
                if (RECEIVER_METHOD_NAME.equals(name)) {
                    attrs.put("groupId", groupIds.get(index++));
                }
            }
            return attrs;
        };
    }





}
