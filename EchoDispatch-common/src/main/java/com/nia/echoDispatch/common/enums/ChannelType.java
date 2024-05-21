package com.nia.echoDispatch.common.enums;

import com.nia.echoDispatch.common.dto.ContentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author nia
 * @description
 * @Date 2024/5/21
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelType implements EnumsInterface{

    /**
     * sms(短信)  -- 腾讯云、云片
     */
    SMS(10, "sms(短信)", "sms",null),
    /**
     * email(邮件) -- QQ、163邮箱
     */
    EMAIL(20, "email(邮件)", "email",null),

    ;
    /**
     * 标识
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 英文简称
     */
    private final String abbrCode;

    /**
     * 内容模型Class
     */
    private final Class<? extends ContentModel> contentModelClass;
}
