package com.nia.echoDispatch.support.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description 使用spring容器监听实现优雅关闭线程池
 * @Date 2024/6/3
 */
@Component
@Slf4j
public class ThreadPoolExecutorShutdownDefinition implements ApplicationListener<ContextClosedEvent> {

    /**
     * 给剩余任务预留时间，到时间后线程池必须销毁
     */
    private static final long AWAIT_TERMINATION = 20;
    /**
     * 时间单位(s)
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final List<ThreadPoolExecutor> POOLS = Collections.synchronizedList(new ArrayList<>(12));


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("容器关闭前处理线程池优雅关闭开始, 当前要处理的线程池数量为: {} >>>>>>>>>>>>>>>>", POOLS.size());
        if (CollectionUtils.isEmpty(POOLS)) {
            return;
        }
        for (ThreadPoolExecutor pool : POOLS) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(AWAIT_TERMINATION, TIME_UNIT)) {
                    log.warn("等待线程池关闭时超时：{}", pool);
                }
            } catch (InterruptedException ex) {
                log.warn("等待线程池关闭时超时：{}", pool);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 注册线程池
     * @param threadPoolExecutor
     */
    public void registryExecutor(ThreadPoolExecutor threadPoolExecutor) {
        POOLS.add(threadPoolExecutor);
    }
}
