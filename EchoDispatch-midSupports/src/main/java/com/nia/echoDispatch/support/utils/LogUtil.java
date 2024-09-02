package com.nia.echoDispatch.support.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.dto.TraceLogDTO;
import com.nia.echoDispatch.common.enums.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author nia
 * @description 日志记录
 * @Date 2024/5/31
 */
@Slf4j
public class LogUtil extends CustomLogListener {

    public static void record(TraceLogDTO logDTO) {
        log.info(JSON.toJSONString(logDTO));
    }
    public static void record(TraceStatus status, Object data) {
        record(TraceLogDTO.builder().traceStatus(status).record(data).build());
    }

    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        log.info(JSON.toJSONString(logDTO));
    }
}
