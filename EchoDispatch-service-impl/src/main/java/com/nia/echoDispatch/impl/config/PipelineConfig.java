package com.nia.echoDispatch.impl.config;

import com.nia.echoDispatch.impl.action.AfterParamCheckProcessor;
import com.nia.echoDispatch.impl.action.AssemblyParamsProcessor;
import com.nia.echoDispatch.impl.action.SendMqProcessor;
import com.nia.echoDispatch.common.enums.BusinessTypeCode;
import com.nia.echoDispatch.common.pipeline.ProcessController;
import com.nia.echoDispatch.common.pipeline.ProcessTemplate;
import com.nia.echoDispatch.impl.action.PreParamCheckProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nia
 * @description pipeline 配置类
 * @Date 2024/5/23
 */
@Configuration
public class PipelineConfig {

    @Autowired
    private PreParamCheckProcessor preParamCheckProcessor;

    @Autowired
    private AssemblyParamsProcessor assemblyParamsProcessor;

    @Autowired
    private AfterParamCheckProcessor afterParamCheckProcessor;

    @Autowired
    private SendMqProcessor sendMqProcessor;

    /**
     * pipeline流程控制器
     * @return
     */
    @Bean("ED-ProcessController")
    public ProcessController processController(){
        ProcessController processController = new ProcessController();
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessorList(Arrays.asList(preParamCheckProcessor,assemblyParamsProcessor,afterParamCheckProcessor,sendMqProcessor));


        Map<String,ProcessTemplate> templateMap = new HashMap<>(4);
        templateMap.put(BusinessTypeCode.SEND.getCode(), processTemplate);

        processController.setTemplateMap(templateMap);
        return processController;

    }

}
