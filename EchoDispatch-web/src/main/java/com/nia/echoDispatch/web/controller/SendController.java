package com.nia.echoDispatch.web.controller;

import com.nia.echoDispatch.common.domian.BatchSendRequest;
import com.nia.echoDispatch.common.domian.SendRequest;
import com.nia.echoDispatch.common.domian.SendResponse;
import com.nia.echoDispatch.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nia
 * @description 发送消息
 * @Date 2024/5/23
 */
@RestController
@RequestMapping("/base")
@Api(tags = "发送消息")
public class SendController {

    @Autowired
    private SendService sendService;


    @ApiOperation(value = "发送接口",notes = "按照对应的渠道以及类型发送消息")
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest){
        return sendService.send(sendRequest);
    }

    @ApiOperation(value = "批量发送接口",notes = "按照对应的渠道以及类型发送消息")
    @PostMapping("/batchSend")
    public SendResponse batchSend(@RequestBody BatchSendRequest batchSendRequest){
        return sendService.batchSend(batchSendRequest);
    }



}
