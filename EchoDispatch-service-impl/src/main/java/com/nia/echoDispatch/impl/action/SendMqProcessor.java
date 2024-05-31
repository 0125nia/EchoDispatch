package com.nia.echoDispatch.impl.action;

import com.alibaba.fastjson.JSON;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.enums.TraceStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.Processor;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.support.mq.MQService;
import com.nia.echoDispatch.support.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nia
 * @description 将消息发送到MQ
 * @Date 2024/5/21
 */
@Service
@Slf4j
public class SendMqProcessor implements Processor {

    @Autowired
    private MQService mqService;

    @Value("${EchoDispatch.mq.topic}")
    private String messageTopic;

    @Value("${EchoDispatch.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext processContext) {
        List<TaskInfo> taskInfos = processContext.getTaskInfos();
        try {
            String message = JSON.toJSONString(taskInfos);
            //发送到MQ
            mqService.send(messageTopic,message);
            //记录日志
            LogUtil.record(TraceStatus.SEND_MQ,message);
            //返回结果
            processContext.setResult(BaseResultVO.success(taskInfos));
        } catch (Exception e) {
            processContext.setResult(BaseResultVO.fail(RespStatus.MQ_SERVICE_ERROR));
            processContext.setBreakFlag(true);
            log.error("send {} fail  e:{}, params:{}",mqPipeline,e.getStackTrace(),JSON.toJSONString(taskInfos));
        }
    }
}
