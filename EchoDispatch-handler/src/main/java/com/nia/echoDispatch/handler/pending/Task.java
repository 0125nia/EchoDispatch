package com.nia.echoDispatch.handler.pending;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.TraceStatus;
import com.nia.echoDispatch.handler.Deduplication.DeduplicationParam;
import com.nia.echoDispatch.handler.Deduplication.DeduplicationService;
import com.nia.echoDispatch.handler.handler.Handler;
import com.nia.echoDispatch.handler.utils.EnumsUtil;
import com.nia.echoDispatch.support.utils.LogUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
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
        LogUtil.record(TraceStatus.DEDUPLICATION,taskInfo);
        TaskInfo deduplication = deduplication(taskInfo);

        if (CollUtil.isEmpty(deduplication.getReceiver())){
            LogUtil.record(TraceStatus.DEDUPLICATION_ERROR,taskInfo);
            return;
        }
        //记录日志
        LogUtil.record(TraceStatus.DEDUPLICATION_SUCCESS,taskInfo);

        //发送逻辑
        //记录日志
        LogUtil.record(TraceStatus.SEND_TO_CHANNEL,deduplication);
        sendHandler(deduplication);

    }


    /**
     * 根据类型发送对应的消息
     * @param taskInfo
     * @return
     */
    private void sendHandler(TaskInfo taskInfo){
        try{
            //获取handler处理对象
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
        DeduplicationParam deduplicationParam = DeduplicationParam.builder().taskInfo(taskInfo).build();
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        // 去重
        deduplicationService.deduplication(deduplicationParam);
        return taskInfo;
    }


}
