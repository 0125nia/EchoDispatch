package com.nia.echoDispatch.cron.config;

import cn.hutool.core.thread.ExecutorBuilder;
import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.ThreadPoolBuilder;
import com.nia.echoDispatch.common.constant.ThreadPoolConstants;

import java.util.concurrent.*;

/**
 * @author nia
 * @description 线程池配置
 * @Date 2024/6/3
 */
public class XxlJobThreadPoolConfig {

    /**
     * 接收到xxl-job请求的线程池名
     */
    public static final String EXECUTE_XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";

    private XxlJobThreadPoolConfig() {
    }

    /**
     * 业务：消费pending队列实际的线程池
     * 配置：核心线程可以被回收，当线程池无被引用且无核心线程数，应当被回收
     * 动态线程池且被Spring管理：false
     *
     * @return
     */
    public static ExecutorService getConsumePendingThreadPool() {
        return ExecutorBuilder.create()
                .setCorePoolSize(ThreadPoolConstants.CORE_POOL_SIZE)
                .setMaxPoolSize(ThreadPoolConstants.MAX_POOL_SIZE)
                .setWorkQueue(new LinkedBlockingQueue(ThreadPoolConstants.BIG_QUEUE_SIZE))
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(ThreadPoolConstants.KEEP_ALIVE_TIME, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 业务：接收到xxl-job请求的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收)
     * 动态线程池且被Spring管理：true
     *
     * @return
     */
    public static DtpExecutor getThreadPoolExecutor() {
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(EXECUTE_XXL_THREAD_POOL_NAME)
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
