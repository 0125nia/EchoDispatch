package com.nia.echoDispatch.service;

import com.nia.echoDispatch.common.domian.BatchSendRequest;
import com.nia.echoDispatch.common.domian.SendRequest;
import com.nia.echoDispatch.common.domian.SendResponse;

public interface SendService {
    /**
     * 单模板单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 单模板多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}