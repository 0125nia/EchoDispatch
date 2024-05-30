package com.nia.echoDispatch.handler.handler.impl;

import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.dto.impl.EmailContentModel;
import com.nia.echoDispatch.handler.handler.Handler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;
import java.util.Set;

/**
 * @author nia
 * @description 邮件发送处理
 * @Date 2024/5/29
 */
@Component
@Slf4j
public class EmailHandler implements Handler {


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 处理发送邮件逻辑
     *
     * @param taskInfo
     */
    @Override
    public void handle(TaskInfo taskInfo) {
        EmailContentModel contentModel = (EmailContentModel) taskInfo.getContentModel();
        Set<String> receivers = taskInfo.getReceiver();

        if (Objects.isNull(contentModel.getUrl())) {
            sendWithoutUrl(receivers, contentModel);
        } else {
            sendWithUrl(receivers, contentModel);
        }
    }

    /**
     * 包含附件的发送方式
     *
     * @param tos
     * @param emailContentModel
     */
    private void sendWithUrl(Set<String> tos, EmailContentModel emailContentModel) {
        try {
            for (String to : tos) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = getBaseHelper(message, to, emailContentModel);
                //添加附件
                File file = new File(emailContentModel.getUrl());
                helper.addAttachment(file.getName(), file);

                mailSender.send(message);
                log.info("带附件的邮件发送成功: {} to :{}", emailContentModel,to);

            }

        } catch (MessagingException e) {
            log.error("带附件的邮件发送失败 : {}", emailContentModel);
            throw new RuntimeException(e);
        }


    }


    /**
     * 不包含附件的发送方式
     *
     * @param tos
     * @param emailContentModel
     */
    private void sendWithoutUrl(Set<String> tos, EmailContentModel emailContentModel) {
        try {
            for (String to : tos) {
                MimeMessage message = mailSender.createMimeMessage();
                getBaseHelper(message, to, emailContentModel);
                mailSender.send(message);
                log.info("普通邮件发送成功: {} to :{}", emailContentModel,to);
            }

        } catch (MessagingException e) {
            log.error("普通邮件发送失败 : {}", emailContentModel);
            throw new RuntimeException(e);
        }
    }


    /**
     * 基础拼装参数逻辑
     * @param message
     * @param to
     * @param emailContentModel
     * @return
     * @throws MessagingException
     */
    private MimeMessageHelper getBaseHelper(MimeMessage message,String to,EmailContentModel emailContentModel) throws MessagingException {
        //创建helper
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //拼装邮件参数
        helper.setTo(to);
        helper.setSubject(emailContentModel.getTitle());
        helper.setText(emailContentModel.getContent());
        helper.setFrom(from);
        //返回helper
        return helper;
    }

}
