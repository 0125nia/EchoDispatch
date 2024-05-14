package com.nia.echoDispatch.common.domian;

import java.util.List;

/**
 * @author nia
 * @description
 * @Date 2024/5/14
 */
public interface SendRequester {
    String getCode();
    Long getMessageTemplateId();
    List<MessageParam> getMessageParamList();
}
