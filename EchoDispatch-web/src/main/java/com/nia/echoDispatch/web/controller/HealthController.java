package com.nia.echoDispatch.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nia
 * @description 健康检测
 * @Date 2024/5/30
 */
@Api("健康检测")
@RestController
public class HealthController {
    @GetMapping("/")
    @ApiOperation("健康检测")
    public String health(){
        return "success";
    }
}
