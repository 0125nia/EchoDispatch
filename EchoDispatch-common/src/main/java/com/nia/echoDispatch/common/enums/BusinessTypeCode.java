package com.nia.echoDispatch.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public enum BusinessTypeCode{

    /**
     * 发送消息
     */
    SEND("send","发送消息"),
    /**
     * 撤回消息
     */
    RECALL("recall","撤回消息");

    /**
     * code 关联着责任链的模板
     */
    private final String code;

    /**
     * 类型说明
     */
    private final String description;

}
