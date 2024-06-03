package com.nia.EchoDispatch.cron.enums;

/**
 * @author nia
 * @description 调度过期策略
 * @Date 2024/6/3
 */
public enum ScheduleExpirationEnum {

    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    ScheduleExpirationEnum(){}

}
