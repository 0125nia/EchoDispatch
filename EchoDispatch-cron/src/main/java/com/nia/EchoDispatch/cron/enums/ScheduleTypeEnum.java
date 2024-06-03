package com.nia.EchoDispatch.cron.enums;

/**
 * @author nia
 * @description 调度类型
 * @Date 2024/6/3
 */
public enum ScheduleTypeEnum {
    /**
     * NONE
     */
    NONE,
    /**
     * schedule by cron
     */
    CRON,

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE;

    ScheduleTypeEnum() {
    }

}
