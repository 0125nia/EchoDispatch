package com.nia.echoDispatch.cron.config;

import com.nia.echoDispatch.common.constant.ThreadPoolConstants;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description 线程池配置
 * @Date 2024/6/3
 */
public class XxlJobThreadPoolConfig {

    private XxlJobThreadPoolConfig() {
    }


    public static ThreadPoolExecutor getThreadPoolExecutor() {
            return new ThreadPoolExecutor(
                    ThreadPoolConstants.CORE_POOL_SIZE,
                    ThreadPoolConstants.MAX_POOL_SIZE,
                    ThreadPoolConstants.KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(ThreadPoolConstants.WORK_QUEUE_SIZE),
                    new ThreadPoolExecutor.AbortPolicy()
            );
    }
}
