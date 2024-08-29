package com.nia.echoDispatch.cron.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.dtp.core.thread.DtpExecutor;
import com.nia.echoDispatch.cron.config.XxlJobThreadPoolConfig;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.mapper.MessageTemplateMapper;
import com.nia.echoDispatch.support.utils.ThreadPoolUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author nia
 * @description
 * @Date 2024/6/3
 */
@Service
@Slf4j
public class XxlJobHandler {
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    @Autowired
    private ThreadPoolUtil threadPoolUtil;
    private DtpExecutor dtpExecutor = XxlJobThreadPoolConfig.getThreadPoolExecutor();

    /**
     * 处理后台的 austin 定时任务消息
     */
    @XxlJob("edJob")
    public void execute() {
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        threadPoolUtil.register(dtpExecutor);

        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        dtpExecutor.execute(() -> handle(messageTemplateId));

    }

    private void handle(Long messageTemplateId) {
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(messageTemplateId);
        if (Objects.isNull(messageTemplate)) {
            return;
        }
        if (CharSequenceUtil.isBlank(messageTemplate.getCronCrowdPath())) {
            log.error("TaskHandler#handle crowdPath empty! messageTemplateId:{}", messageTemplateId);
            return;
        }


    }

}
