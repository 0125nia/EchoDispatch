package com.nia.echoDispatch.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author nia
 * @description 发送的消息类型
 * @Date 2024/5/21
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageType{
    /**
     * 通知类消息
     */
    NOTICE(10, "通知类消息"),
    /**
     * 营销类消息
     */
    MARKETING(20, "营销类消息"),
    /**
     * 验证码消息
     */
    AUTH_CODE(30, "验证码消息");

    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

}
