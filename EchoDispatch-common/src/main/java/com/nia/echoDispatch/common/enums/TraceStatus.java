package com.nia.echoDispatch.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nia
 * @description 链路追踪状态枚举类
 * @Date 2024/5/31
 */
@Getter
@AllArgsConstructor
public enum TraceStatus {

    SEND_MQ("t0001","发送到MQ"),
    CONSUME("t0002","MQ消费"),
    CONSUME_ERROR("t0003","MQ消费出错"),
    PENDING("t0004","延迟分发到线程池"),
    DEDUPLICATION("t0005","去重操作"),
    DEDUPLICATION_SUCCESS("t0006","通过去重成功"),
    DEDUPLICATION_ERROR("t0007","去重后结果错误"),
    SEND_TO_CHANNEL("t0008","发送到对应渠道"),

    ;


    private final String code;

    private final String status;


}
