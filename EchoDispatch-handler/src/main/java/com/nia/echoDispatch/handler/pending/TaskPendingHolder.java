package com.nia.echoDispatch.handler.pending;

import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.handler.pool.ThreadPoolConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    /**
     * 给每个渠道，每种类型初始化一个线程池
     */
    @PostConstruct
    public void init(){
        for (ChannelType channelType : ChannelType.values()) {
            ThreadPoolExecutor executor = newPool();
            holder.put(channelType.getAbbrCode(),executor);
        }
    }


    public static ThreadPoolExecutor newPool(){
        return new ThreadPoolExecutor(
                ThreadPoolConstants.CORE_POOL_SIZE,
                ThreadPoolConstants.MAX_POOL_SIZE,
                ThreadPoolConstants.KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(ThreadPoolConstants.WORK_QUEUE_SIZE),
                new ThreadPoolExecutor.AbortPolicy()
                );
    }

    public void pendingAndExecute(String code,Task task) {
        holder.get(code).execute(task);
    }
}
