package com.nia.echoDispatch.handler.config;


import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.ThreadPoolBuilder;
import com.nia.echoDispatch.common.constant.ThreadPoolConstants;

import java.util.concurrent.TimeUnit;

public class HandlerThreadPoolConfig {

    private static final String PRE_FIX = "echo-Dispatch.";

    private HandlerThreadPoolConfig() {

    }
    /**
     * 业务：处理某个渠道的某种类型消息的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收)
     * 动态线程池且被Spring管理：true
     *
     * @return
     */
    public static DtpExecutor getExecutor(String groupId) {
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(PRE_FIX + groupId)
                .corePoolSize(ThreadPoolConstants.CORE_POOL_SIZE)
                .maximumPoolSize(ThreadPoolConstants.MAX_POOL_SIZE)
                .keepAliveTime(ThreadPoolConstants.KEEP_ALIVE_TIME)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                .allowCoreThreadTimeOut(false)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), ThreadPoolConstants.WORK_QUEUE_SIZE, false)
                .buildDynamic();
    }
}
