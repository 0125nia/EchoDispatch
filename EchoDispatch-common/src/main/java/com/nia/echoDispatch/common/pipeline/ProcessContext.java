package com.nia.echoDispatch.common.pipeline;


import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.common.domian.SendRequester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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
     * 责任链中断标识
     */
    private boolean breakFlag;
    /**
     * 返回结果
     */
    private BaseResultVO result;


}
