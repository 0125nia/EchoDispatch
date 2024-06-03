package com.nia.echoDispatch.common.constant;

/**
 * @author nia
 * @description 基础常量类
 * @Date 2024/5/14
 */
public class BasicConstant {
    /**
     * 最大接收者个数
     */
    public static final Integer MAX_RECEIVER_NUM = 100;


    /**
     * 指定时间内发送的最大消息数
     */
    public static final Integer MAX_MESSAGE_IN_SET_TIME = 5;

    /**
     * redis 键过期时间(单位HOURS)
     */
    public static final Long MAX_SEND_A_DAY = 24L;

    /**
     * boolean-true
     */
    public static final Integer TRUE = 1;
    /**
     * boolean-false
     */
    public static final Integer FALSE = 0;

    /**
     * 日期格式
     */
    public static final String CRON_FORMAT = "ss mm HH dd MM ? yyyy-yyyy";
    /**
     * ？秒/天
     */
    public static final Long ONE_DAY_SECOND = 86400L;



    private BasicConstant(){}
}
