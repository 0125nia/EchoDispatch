package com.nia.echoDispatch.common.pipeline;


import com.nia.echoDispatch.common.domian.SendRequester;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessContext {

    /**
     * 责任链标识
     */
    private String type;
    /**
     * 存储上下文数据
     */
    private SendRequester sendRequest;
    /**
     * 参数拼装结果(任务信息)
     */
    private List<TaskInfo> taskInfos;
    /**
     * 责任链中断标识
     */
    private boolean breakFlag;
    /**
     * 返回结果
     */
    private BaseResultVO result;


}
