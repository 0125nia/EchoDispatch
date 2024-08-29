package com.nia.echoDispatch.support.utils;

import com.dtp.core.thread.DtpExecutor;
import com.nia.echoDispatch.support.config.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author nia
 * @description 线程池工具类
 * @Date 2024/6/3
 */
@Component
public class ThreadPoolUtil {

    private static final String SOURCE_NAME = "echo-dispatch";
    @Autowired
    private ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    /**
     * 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        shutdownDefinition.registryExecutor(dtpExecutor);
    }



}
