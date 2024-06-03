package com.nia.echoDispatch.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author nia
 * @description 审核状态
 * @Date 2024/6/3
 */
@Getter
@ToString
@AllArgsConstructor
public enum AuditStatus {

    /**
     * 10.待审核
     */
    WAIT_AUDIT(10, "待审核"),
    /**
     * 20.审核成功
     */
    AUDIT_SUCCESS(20, "审核成功"),
    /**
     * 30.被拒绝'
     */
    AUDIT_REJECT(30, "被拒绝");

    private final Integer code;
    private final String description;
}