package com.nia.echoDispatch.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @RequestMapping("/test")
    private String test(){
        System.out.println("sout输出");
        log.info("log:{}","日志输出");
        return "testing";
    }
}
