package com.nia.echoDispatch.handler.utils;

import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.common.enums.EnumsInterface;
import com.nia.echoDispatch.common.enums.MessageType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author nia
 * @description
 * @Date 2024/5/21
 */

@Slf4j
public class GroupIdUtils {
    /**
     * 获取所有的groupIds
     * (不同的渠道不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds(){
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType: ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getAbbrCode() + "." + messageType.getAbbr());
            }
        }
        return groupIds;
    }

    /**
     * 通过TaskInfo找到对应的枚举类从而拼接为GroupId
     * @param taskInfo 任务信息
     * @return
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        Optional<ChannelType> channelType = Optional.ofNullable(getEnumByCode(taskInfo.getSendChannel(), ChannelType.class));
        Optional<MessageType> messageType = Optional.ofNullable(getEnumByCode(taskInfo.getMsgType(), MessageType.class));
        if (channelType.isPresent()&&messageType.isPresent()) {
            return channelType.get().getAbbrCode() + messageType.get().getAbbr();
        }
        log.error("ChannelType code : {} or MessageType code : {} 未能找到对应枚举对象",taskInfo.getSendChannel(),taskInfo.getMsgType());
        throw new RuntimeException("未能找到对应枚举对象");
    }


    /**
     * 通过code及枚举类找到对应的枚举对象
     * @param code code
     * @param enumClass 枚举类
     * @return 枚举对象
     * @param <T>
     */
    public static  <T extends EnumsInterface> T getEnumByCode(Integer code,Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.getCode(),code)) {
                return enumConstant;
            }
        }
        return null;
    }
}
