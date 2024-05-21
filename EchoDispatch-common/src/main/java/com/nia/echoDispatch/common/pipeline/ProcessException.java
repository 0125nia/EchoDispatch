package com.nia.echoDispatch.common.pipeline;

/**
 * @author nia
 * @description
 * @Date 2024/5/14
 */
public class ProcessException extends RuntimeException{
    /**
     * 流程处理上下文
     */
    private final ProcessContext processContext;

    public ProcessException(ProcessContext processContext) {
        super();
        this.processContext = processContext;
    }

    public ProcessException(ProcessContext processContext, Throwable cause) {
        super(cause);
        this.processContext = processContext;
    }

//    @Override
//    public String getMessage() {
//        if (Objects.nonNull(this.processContext)) {
//            return this.processContext.getResult().getMsg();
//        }
//        return RespStatus.CONTEXT_IS_NULL.getMsg();
//
//    }

    public ProcessContext getProcessContext() {
        return processContext;
    }
}
