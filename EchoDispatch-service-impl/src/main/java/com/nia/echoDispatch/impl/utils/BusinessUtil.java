package com.nia.echoDispatch.impl.utils;

import com.nia.echoDispatch.support.domain.MessageTemplate;

/**
 * @author nia
 * @description Business工具类
 * @Date 2024/5/20
 */
public class BusinessUtil {

    /**
     * 生成业务唯一标识id  "模板类型 + 模板id + 时间戳"
     * @param messageTemplate 模板
     * @return 唯一标识
     */
    public static Long generateTaskBusinessId(MessageTemplate messageTemplate){
        return messageTemplate.getTemplateType() + messageTemplate.getId() + System.currentTimeMillis();
    }
}
