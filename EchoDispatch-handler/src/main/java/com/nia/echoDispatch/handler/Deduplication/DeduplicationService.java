package com.nia.echoDispatch.handler.Deduplication;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.constant.BasicConstant;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.domian.dto.TraceLogDTO;
import com.nia.echoDispatch.common.enums.TraceStatus;
import com.nia.echoDispatch.support.utils.LogUtil;
import com.nia.echoDispatch.support.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * @author nia
 * @description 使用redis实现去重
 * @Date 2024/5/23
 */

@Service
@Slf4j
public class DeduplicationService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${EchoDispatch.redis.key.deduplication}")
    private String HEAD;
    @Autowired
    private SlideWindowDeduplicationService deduplicationService;

    /**
     * 去重：检查5min内是否发送了重复的信息 以及 是否在一天内发送超5条信息
     */
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();

        Set<String> filterReceiver = deduplicationService.filter(taskInfo, param);

        // 剔除符合去重条件的用户
        if (CollUtil.isNotEmpty(filterReceiver)) {
            taskInfo.getReceiver().removeAll(filterReceiver);
            LogUtil.record(TraceLogDTO.builder().traceStatus(TraceStatus.DEDUPLICATION).record(taskInfo).build());
        }
    }

}
