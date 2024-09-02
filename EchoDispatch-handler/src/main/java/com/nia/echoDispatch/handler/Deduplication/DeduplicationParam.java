package com.nia.echoDispatch.handler.Deduplication;

import com.alibaba.fastjson.annotation.JSONField;
import com.nia.echoDispatch.common.domian.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nia
 * @description
 * @Date 2024/8/29
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {
    /**
     * TaskIno信息
     */
    private TaskInfo taskInfo;
    /**
     * 去重时间
     * 单位：秒
     */
    private Long deduplicationTime;
}
