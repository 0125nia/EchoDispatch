package com.nia.echoDispatch.service;

import com.nia.echoDispatch.domain.BatchSendRequest;
import com.nia.echoDispatch.domain.SendRequest;
import com.nia.echoDispatch.domain.SendResponse;

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