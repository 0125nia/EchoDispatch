package com.nia.echoDispatch.cron.enums;

/**
 * @author nia
 * @description 路由策略枚举
 * @Date 2024/6/3
 */
public enum ExecutorRouteEnum {

    /**
     * FIRST
     */
    FIRST,
    /**
     * LAST
     */
    LAST,
    /**
     * ROUND
     */
    ROUND,
    /**
     * RANDOM
     */
    RANDOM,
    /**
     * CONSISTENT_HASH
     */
    CONSISTENT_HASH,
    /**
     * LEAST_FREQUENTLY_USED
     */
    LEAST_FREQUENTLY_USED,
    /**
     * LEAST_RECENTLY_USED
     */
    LEAST_RECENTLY_USED,
    /**
     * FAILOVER
     */
    FAILOVER,
    /**
     * BUSYOVER
     */
    BUSYOVER,
    /**
     * SHARDING_BROADCAST
     */
    SHARDING_BROADCAST;

    ExecutorRouteEnum() {
    }
}
