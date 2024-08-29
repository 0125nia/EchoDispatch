package com.nia.echoDispatch.common.constant;

/**
 * @author nia
 * @description 线程池常量类
 * @Date 2024/5/22
 */
public class ThreadPoolConstants {

    /**
     * 核心线程数
     */
    public static final Integer CORE_POOL_SIZE = 2;
    /**
     * 最大线程数
     */
    public static final Integer MAX_POOL_SIZE = 2;
    /**
     * 空闲线程超时时间
     */
    public static final Integer KEEP_ALIVE_TIME = 60;
    /**
     * 工作队列大小
     */
    public static final Integer WORK_QUEUE_SIZE = 128;
    /**
     * 大任务队列大小
     */
    public static final Integer BIG_QUEUE_SIZE = 1024;


}
