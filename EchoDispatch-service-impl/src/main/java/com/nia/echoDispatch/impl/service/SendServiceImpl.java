package com.nia.echoDispatch.impl.service;

import cn.hutool.core.util.ObjectUtil;
import com.nia.echoDispatch.common.domian.BatchSendRequest;
import com.nia.echoDispatch.common.domian.SendRequest;
import com.nia.echoDispatch.common.domian.SendResponse;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.ProcessController;
import com.nia.echoDispatch.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author nia
 * @description 发送接口
 * @Date 2024/5/23
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    @Qualifier("ED-ProcessController")
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {
        if (ObjectUtil.isEmpty(sendRequest)) {
            return new SendResponse(RespStatus.REQUEST_ERROR.getCode(),RespStatus.REQUEST_ERROR.getMsg());
        }
        ProcessContext context = ProcessContext.builder()
                .sendRequest(sendRequest)
                .type(sendRequest.getCode())
                .breakFlag(false)
                .build();

        ProcessContext processContext = processController.process(context);

        return new SendResponse(processContext.getResult().getCode(),processContext.getResult().getMsg());
    }

    @Override
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {

        //todo 完善逻辑
        return null;
    }
}
