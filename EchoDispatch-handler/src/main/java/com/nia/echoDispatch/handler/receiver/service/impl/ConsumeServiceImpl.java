package com.nia.echoDispatch.handler.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.handler.pending.Task;
import com.nia.echoDispatch.handler.pending.TaskPendingHolder;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.handler.utils.EnumsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author nia
 * @description 消费者业务逻辑实现
 * @Date 2024/5/21
 */
@Service
@Slf4j
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Override
    public void consumeSendMsg(List<TaskInfo> taskInfoList) {
        Integer sendChannel = CollUtil.getFirst(taskInfoList).getSendChannel();
        ChannelType channelTypeByCode = EnumsUtils.getChannelTypeByCode(sendChannel);
        if (Objects.isNull(channelTypeByCode)){
            log.error("code: {} 对应的 ChannelType 不存在",sendChannel);
            throw new RuntimeException("code对应的ChannelType不存在");
        }
        for (TaskInfo taskInfo : taskInfoList) {
            Task task = new Task();
            task.setTaskInfo(taskInfo);
            taskPendingHolder.pendingAndExecute(channelTypeByCode.getAbbrCode(),task);
        }
    }
}
