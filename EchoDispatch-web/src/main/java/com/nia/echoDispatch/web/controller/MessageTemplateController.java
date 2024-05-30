package com.nia.echoDispatch.web.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.nia.echoDispatch.impl.service.MessageTemplateService;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * @author nia
 * @description 消息模板
 * @Date 2024/5/30
 */
@Slf4j
@Api("消息模板")
@RestController
@RequestMapping("/messageTemplate")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Value("${EchoDispatch.data.path}")
    private String dataPath;

    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     * @param messageTemplate
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("/保存数据")
    public boolean save(@RequestBody MessageTemplate messageTemplate) {
        return messageTemplateService.saveOrUpdate(messageTemplate);
    }


    @GetMapping("query/{id}")
    @ApiOperation("根据id查找消息模板")
    public MessageTemplate queryById(@PathVariable Long id){
        return messageTemplateService.getById(id);
    }


    /**
     * 上传人群文件
     */
    @PostMapping("upload")
    @ApiOperation("/上传人群文件")
    public Map<Object, Object> upload(@RequestParam("file") MultipartFile file) {
        String filePath = dataPath + IdUtil.fastSimpleUUID() + file.getOriginalFilename();
        try {
            File localFile = new File(filePath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            file.transferTo(localFile);
        } catch (Exception e) {
            log.error("MessageTemplateController#upload fail! e:{},params{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(file));
            throw new RuntimeException(e);
        }
        return MapUtil.of(new String[][]{{"value", filePath}});
    }

}
