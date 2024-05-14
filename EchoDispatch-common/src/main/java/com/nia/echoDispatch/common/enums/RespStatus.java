package com.nia.echoDispatch.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author nia
 * @description
 * @Date 2024/5/14
 */

@Getter
@AllArgsConstructor
@ToString
public enum RespStatus {

    /**
     * 错误
     */
    ERROR("500","服务器未知错误"),


    /**
     * 响应
     */
    SUCCESS("200","操作成功"),
    FAIL("-1","操作失败"),

    /**
     * pipeline责任链
     */
    PRE_PARAM_PROBLEM("P0001","责任链前置参数错误"),


    ;

    private final String code;
    private final String msg;
    }
