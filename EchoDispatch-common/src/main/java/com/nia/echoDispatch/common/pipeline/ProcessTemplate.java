package com.nia.echoDispatch.common.pipeline;


import lombok.Data;

import java.util.List;

@Data
public class ProcessTemplate {
    private List<Processor> processorList;
}
