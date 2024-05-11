package com.nia.echoDispatch.service;

import com.nia.echoDispatch.domain.SendRequest;
import com.nia.echoDispatch.domain.SendResponse;

public interface RecallService {

    /**
     * 根据模板id撤回消息
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
