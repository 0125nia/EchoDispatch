package com.nia.echoDispatch.handler.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.handler.pending.Task;
import com.nia.echoDispatch.handler.pending.TaskPendingHolder;
import com.nia.echoDispatch.handler.receiver.service.ConsumeService;
import com.nia.echoDispatch.handler.utils.GroupIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nia
 * @description 消费者业务逻辑实现
 * @Date 2024/5/21
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Override
    public void consumeSendMsg(List<TaskInfo> taskInfoList) {
        String groupId = GroupIdUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoList));
        for (TaskInfo taskInfo : taskInfoList) {
            Task task = new Task();
            task.setTaskInfo(taskInfo);
            taskPendingHolder.route(groupId).execute(task);
        }
    }
}
