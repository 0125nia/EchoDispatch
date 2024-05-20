package com.nia.serviceImpl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.nia.echoDispatch.common.constant.BasicConstant;
import com.nia.echoDispatch.common.domian.MessageParam;
import com.nia.echoDispatch.common.domian.SendRequester;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.Processor;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author nia
 * @description 责任链：前置参数校验
 * @Date 2024/5/14
 */
@Service
public class PreParamCheckProcessor implements Processor {
    @Override
    public void process(ProcessContext processContext) {
        SendRequester sendRequest = processContext.getSendRequest();
        Long messageTemplateId = sendRequest.getMessageTemplateId();
        List<MessageParam> messageParamList = sendRequest.getMessageParamList();

        // 1. 没有传入 消息模板Id 或者 messageParam
        if (Objects.isNull(messageTemplateId) || CollUtil.isEmpty(messageParamList)){
            processContext.setBreakFlag(true);
            processContext.setResult(new BaseResultVO<>(RespStatus.PRE_PARAM_PROBLEM,"模板ID或参数列表为空"));
        }

        // 2. 过滤 receiver=null 的 messageParam
        List<MessageParam> resultMessageParamList = messageParamList.stream()
                .filter(messageParam -> !CharSequenceUtil.isBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(resultMessageParamList)) {
            processContext.setBreakFlag(true);
            processContext.setResult(BaseResultVO.fail(RespStatus.PRE_PARAM_PROBLEM, "含接受者的参数列表为空"));
            return;
        }

        // 3. 过滤 超出最大receiver 的请求
        if (resultMessageParamList.stream().anyMatch(messageParam -> messageParam.getReceiver().split(StrPool.COMMA).length > BasicConstant.MAX_RECEIVER_NUM)) {
            processContext.setBreakFlag(true);
            processContext.setResult(BaseResultVO.fail(RespStatus.PRE_PARAM_PROBLEM,"接收者数量超出范围"));
        }
    }
}
