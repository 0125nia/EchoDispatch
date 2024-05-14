package com.nia.echoDispatch.service;

import com.nia.echoDispatch.common.domian.SendRequest;
import com.nia.echoDispatch.common.domian.SendResponse;

public interface RecallService {

    /**
     * 根据模板id撤回消息
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
