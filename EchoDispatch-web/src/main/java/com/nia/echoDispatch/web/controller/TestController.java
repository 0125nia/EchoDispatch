package com.nia.echoDispatch.web.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.web.service.impl.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @RequestMapping("/test")
    private String test(){
        System.out.println("sout输出");
        log.info("log:{}","日志输出");
        return "testing";
    }

    @RequestMapping("/database")
    public String testDataBase(){
        List<MessageTemplate> messageTemplates = messageTemplateService.findAllByUpdatedDesc(0, new Page<>(0, 10));
        return JSON.toJSONString(messageTemplates);
    }
}
