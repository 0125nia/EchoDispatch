package com.nia.echoDispatch.common.pipeline;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProcessController {

    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateMap = null;

    public ProcessContext process(ProcessContext context) {
        ProcessTemplate processTemplate = templateMap.get(context.getType());
        List<Processor> processorList = processTemplate.getProcessorList();
        for (Processor processor : processorList) {
            processor.process(context);
            if (context.isBreakFlag()) {
                break;
            }
        }
        return context;
    }
}
