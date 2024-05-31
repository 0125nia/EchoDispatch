package com.nia.echoDispatch.common.domian.dto;

import com.nia.echoDispatch.common.enums.TraceStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author nia
 * @description
 * @Date 2024/5/31
 */
@Data
@Builder
public class TraceLogDTO {
    private TraceStatus traceStatus;
    private Object record;
}
