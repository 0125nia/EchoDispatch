package com.nia.serviceImpl.action;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.domian.MessageParam;
import com.nia.echoDispatch.common.domian.SendRequester;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.dto.ContentModel;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.Processor;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.mapper.MessageTemplateMapper;
import com.nia.serviceImpl.utils.BusinessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nia
 * @description 责任链：拼装参数
 * @Date 2024/5/20
 */
@Service
public class AssemblyParamsProcessor implements Processor {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    @Override
    public void process(ProcessContext processContext) {
        SendRequester sendRequest = processContext.getSendRequest();
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(sendRequest.getMessageTemplateId());
        if (messageTemplate == null){
            processContext.setResult(BaseResultVO.fail(RespStatus.TEMPLATE_ID_ERROR));
            processContext.setBreakFlag(true);
            return;
        }

        Set<String> receivers = getReceivers(sendRequest.getMessageParamList());
        ContentModel contentModel = getContentModel(sendRequest.getMessageParamList());


        TaskInfo taskInfo = TaskInfo.builder()
                .messageTemplateId(messageTemplate.getId())
                .taskBusinessId(BusinessUtils.generateTaskBusinessId(messageTemplate))
                .receiver(receivers)
                .sendChannel(messageTemplate.getSendChannel())
                .contentModel(contentModel)
                .msgType(messageTemplate.getMsgType())
                .build();

        ArrayList<TaskInfo> taskInfos = CollUtil.newArrayList(taskInfo);
        processContext.setTaskInfos(taskInfos);

    }

    /**
     * 获取内容模板对象
     * @param messageParamList 信息参数列表
     * @return 内容模板对象
     */
    private ContentModel getContentModel(List<MessageParam> messageParamList) {
        for (MessageParam messageParam : messageParamList) {
            Map<String, String> variables = messageParam.getVariables();
        }
        //TODO 补充反射的方式获取内容模板
        return null;
    }

    /**
     * 获取接收者集合
     *
     * @param messageParamList 信息参数列表
     * @return 接收者集合
     */
    private Set<String> getReceivers(List<MessageParam> messageParamList) {
        Set<String> receivers = new HashSet<>();
        for (MessageParam messageParam : messageParamList) {
            receivers.add(messageParam.getReceiver());
        }
        return receivers;
    }
}
