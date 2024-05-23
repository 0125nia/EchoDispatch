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
     * 请求
     */
    REQUEST_ERROR("R0001","请求参数错误"),

    /**
     * 响应
     */
    SUCCESS("200","操作成功"),
    FAIL("-1","操作失败"),

    /**
     * pipeline责任链
     */
    PRE_PARAM_PROBLEM("P0001","责任链前置参数错误"),
    TEMPLATE_ID_ERROR("P0002","模板id错误"),
    TASK_INFO_IS_NULL("P0003","任务信息列表为空"),
    ALL_RECEIVER_IS_INVALID("P0004","所有的发送任务中的接收者（电话号码/邮箱）格式均无效，无法发送"),
    MQ_SERVICE_ERROR("P0005","消息队列MQ服务异常"),

    ;

    private final String code;
    private final String msg;
}
