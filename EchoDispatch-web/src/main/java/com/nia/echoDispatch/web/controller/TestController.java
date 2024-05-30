package com.nia.echoDispatch.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.dto.impl.EmailContentModel;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.common.enums.MessageType;
import com.nia.echoDispatch.handler.Deduplication.DeduplicationService;
import com.nia.echoDispatch.handler.handler.impl.EmailHandler;
import com.nia.echoDispatch.impl.service.MessageTemplateService;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.service.NacosService;
import com.nia.echoDispatch.support.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private DeduplicationService deduplicationService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private NacosService nacosService;

    @Autowired
    private EmailHandler emailHandler;


    @RequestMapping("/test")
    private String test() {
        System.out.println("sout输出");
        log.info("log:{}", "日志输出");
        return "testing";
    }

    @RequestMapping("/database")
    public String testDataBase() {
        List<MessageTemplate> messageTemplates = messageTemplateService.findAllByUpdatedDesc(0, new Page<>(0, 10));
        return JSON.toJSONString(messageTemplates);
    }

    @RequestMapping("/redis")
    public String redis() {
        redisUtils.put("abc", "111");
        String s = redisUtils.get("abc");
        System.out.println(s);
        return s;
    }

    @RequestMapping("/deduplicate")
    public String deduplicate() {
        boolean b = deduplicationService.deduplication("sms.notice", "nia", "12343");
        System.out.println(b);

        if (b) {
            return "ok";
        }
        return "need deduplicate";
    }

    @RequestMapping("/nacos")
    public String nacos(){
        return nacosService.getProperty("test");
    }

    @RequestMapping("/mail")
    public String mail(){
        EmailContentModel emailContentModel = EmailContentModel.builder()
                .title("EchoDispatch")
                .content("test email send")
                .build();


        TaskInfo taskInfo = TaskInfo.builder()
                .receiver(CollUtil.set(false,"nia220125@163.com"))
                .msgType(MessageType.NOTICE.getCode())
                .sendChannel(ChannelType.EMAIL.getCode())
                .contentModel(emailContentModel)
                .build();
        emailHandler.handle(taskInfo);
        return "ok";
    }

    @RequestMapping("/mail2")
    public String mail2(){
        EmailContentModel emailContentModelUrl = EmailContentModel.builder()
                .title("EchoDispatch")
                .content("test email send")
                .url("")
                .build();

        TaskInfo taskInfo = TaskInfo.builder()
                .receiver(CollUtil.set(false,""))
                .msgType(MessageType.NOTICE.getCode())
                .sendChannel(ChannelType.EMAIL.getCode())
                .contentModel(emailContentModelUrl)
                .build();
        emailHandler.handle(taskInfo);
        return "ok";
    }


}
