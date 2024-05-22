package com.nia.echoDispatch.handler.receiver.service;

import com.nia.echoDispatch.common.domian.TaskInfo;

import java.util.List;

/**
 * @author nia
 * @description 消费接收到的消息
 * @Date 2024/5/21
 */
public interface ConsumeService {
    void consumeSendMsg(List<TaskInfo> taskInfoList);
}
