package com.nia.echoDispatch.common.pipeline;

public interface Processor {
    /**
     * 处理逻辑
     * @param processContext
     */
    void process(ProcessContext processContext);
}
