package com.nia.echoDispatch.handler.utils;

import com.nia.echoDispatch.common.enums.ChannelType;

/**
 * @author nia
 * @description
 * @Date 2024/5/28
 */
public class EnumsUtils {
    public static ChannelType getChannelTypeByCode(Integer code){
        for (ChannelType value : ChannelType.values()) {
            if (code.equals(value.getCode())){
                return value;
            }
        }
        return null;
    }
}
