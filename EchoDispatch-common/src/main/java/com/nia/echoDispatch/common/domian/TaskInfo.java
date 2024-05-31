package com.nia.echoDispatch.common.domian;

import com.nia.echoDispatch.common.model.ContentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author nia
 * @description
 * @Date 2024/5/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {
    /**
     * 任务业务唯一标识
     */
    private Long taskBusinessId;

    /**
     * 模板id
     */
    private Long messageTemplateId;

    /**
     * 接收者
     */
    private Set<String> receiver;

    /**
     * 参数
     */
    private ContentModel contentModel;

    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 消息类型
     */
    private Integer msgType;

}
