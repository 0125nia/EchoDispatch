package com.nia.echoDispatch.common.pipeline;


import com.nia.echoDispatch.common.domian.SendRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private SendRequest sendRequest;
    /**
     * 责任链中断标识
     */
    private boolean breakFlag;
    /**
     * 返回结果
     */
    private String result;


}
