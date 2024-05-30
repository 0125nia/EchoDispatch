package com.nia.echoDispatch.handler.pending;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.handler.Deduplication.DeduplicationService;
import com.nia.echoDispatch.handler.handler.Handler;
import com.nia.echoDispatch.handler.utils.EnumsUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nia
 * @description 对TaskInfo分线程处理
 * @Date 2024/5/22
 */
@Component
@Slf4j
@Data
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{

    private TaskInfo taskInfo;

    @Autowired
    private DeduplicationService deduplicationService;

    @Autowired
    @Qualifier("handlerMap")
    private Map<Integer,Handler> handlerMap;

    @Override
    public void run() {
        //去重逻辑
        TaskInfo deduplication = deduplication(taskInfo);

        if (CollUtil.isEmpty(deduplication.getReceiver())){
            return;
        }

        //发送逻辑
        sendHandler(deduplication);

    }


    /**
     * 根据类型发送对应的消息
     * @param taskInfo
     * @return
     */
    private void sendHandler(TaskInfo taskInfo){
        try{
            Handler handler = handlerMap.get(taskInfo.getSendChannel());
            handler.handle(taskInfo);
        }catch (Exception e){
            log.error("Task#sendHandler error :{}",e.getMessage());
        }
    }


    /**
     * 根据去重逻辑过滤接收者
     * @param taskInfo
     * @return
     */
    private TaskInfo deduplication(TaskInfo taskInfo){
        String msg = JSON.toJSONString(taskInfo.getContentModel());
        Integer sendChannel = taskInfo.getSendChannel();
        String code = Objects.requireNonNull(EnumsUtils.getChannelTypeByCode(sendChannel)).getAbbrCode();
        //使用流过滤处理接收者Set
        Set<String> receivers = taskInfo.getReceiver().stream()
                .filter(receiver -> deduplicationService.deduplication(code,receiver,msg))
                .collect(Collectors.toSet());

        //重新装配
        taskInfo.setReceiver(receivers);
        return taskInfo;
    }


}
