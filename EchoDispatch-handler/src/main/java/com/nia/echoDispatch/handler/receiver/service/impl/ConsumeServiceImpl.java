package com.nia.echoDispatch.handler.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.handler.utils.GroupIdUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nia
 * @description
 * @Date 2024/5/21
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {
    @Override
    public void consumeSendMsg(List<TaskInfo> taskInfoList) {
        String groupId = GroupIdUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoList));
    }
}
