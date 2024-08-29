package com.nia.echoDispatch.handler.pending;

import com.dtp.core.thread.DtpExecutor;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.common.constant.ThreadPoolConstants;
import com.nia.echoDispatch.handler.config.HandlerThreadPoolConfig;
import com.nia.echoDispatch.support.utils.ThreadPoolUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description 延迟处理task，分配线程池
 * @Date 2024/5/22
 */
@Component
public class TaskPendingHolder {


    /**
     * 存储线程池与消息类型的映射
     */
    private Map<String, ThreadPoolExecutor> holder = new HashMap<>(32);
    @Autowired
    private ThreadPoolUtil threadPoolUtils;

    /**
     * 给每个渠道，每种类型初始化一个线程池
     */
    @PostConstruct
    public void init(){
        for (ChannelType channelType : ChannelType.values()) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(channelType.getAbbrCode());
            //注册线程池
            threadPoolUtils.register(executor);
            holder.put(channelType.getAbbrCode(),executor);
        }
    }


    public void pendingAndExecute(String code,Task task) {
        holder.get(code).execute(task);
    }
}
