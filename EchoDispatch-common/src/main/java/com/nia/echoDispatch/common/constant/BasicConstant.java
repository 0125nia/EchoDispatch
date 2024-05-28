package com.nia.echoDispatch.common.constant;

/**
 * @author nia
 * @description
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




    private BasicConstant(){}
}
