package com.nia.echoDispatch.impl.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nia.echoDispatch.common.enums.AuditStatus;
import com.nia.echoDispatch.common.enums.MessageStatus;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.cron.domain.XxlJobInfo;
import com.nia.echoDispatch.cron.service.XxlJobService;
import com.nia.echoDispatch.common.constant.DataConstants;
import com.nia.echoDispatch.service.IMessageTemplateService;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.mapper.MessageTemplateMapper;
import com.nia.echoDispatch.cron.utils.XxlJobUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MessageTemplateService extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {

    @Autowired
    private XxlJobUtil xxlJobUtil;

    @Autowired
    private XxlJobService xxlJobService;

    @Override
    public List<MessageTemplate> findAllByUpdatedDesc(Integer deleted, Page<MessageTemplate> page) {
        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataConstants.MESSAGE_TEMPLATE_IS_DELETED,deleted).orderByDesc(DataConstants.MESSAGE_TEMPLATE_UPDATE);
        return baseMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public Long countByIsDeletedEquals(Integer deleted) {
        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataConstants.MESSAGE_TEMPLATE_IS_DELETED,deleted);
        return baseMapper.selectCount(queryWrapper);
    }

    public BaseResultVO startCronTask(Long id) {
        // 1.获取消息模板的信息
        MessageTemplate messageTemplate = getById(id);
        if (Objects.isNull(messageTemplate)) {
            return BaseResultVO.fail();
        }

        // 2.动态创建或更新定时任务
        XxlJobInfo xxlJobInfo = xxlJobUtil.buildXxlJobInfo(messageTemplate);

        // 3.获取taskId(如果本身存在则复用原有任务，如果不存在则得到新建后任务ID)
        Integer taskId = messageTemplate.getCronTaskId();
        BaseResultVO basicResultVO = xxlJobService.saveCronTask(xxlJobInfo);
        if (Objects.isNull(taskId) && RespStatus.SUCCESS.getCode().equals(basicResultVO.getCode()) && Objects.nonNull(basicResultVO.getData())) {
            taskId = Integer.valueOf(String.valueOf(basicResultVO.getData()));
        }

        // 4. 启动定时任务
        if (Objects.nonNull(taskId)) {
            xxlJobService.startCronTask(taskId);
            messageTemplate.setMsgStatus(MessageStatus.RUN.getCode());
            messageTemplate.setCronTaskId(taskId);
            messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
            MessageTemplate clone = ObjectUtil.clone(messageTemplate);
            save(clone);
            return BaseResultVO.success();
        }
        return BaseResultVO.fail();
    }

    /**
     * 暂停模板的定时任务
     *
     * @param id
     * @return
     */
    public BaseResultVO stopCronTask(Long id) {
        // 1.修改模板状态
        MessageTemplate messageTemplate = getById(id);
        if (Objects.isNull(messageTemplate)) {
            return BaseResultVO.fail();
        }
        messageTemplate.setMsgStatus(MessageStatus.STOP.getCode());
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        MessageTemplate clone = ObjectUtil.clone(messageTemplate);
        save(clone);

        // 2.暂停定时任务
        return xxlJobService.stopCronTask(clone.getCronTaskId());
    }

    /**
     * 1. 重置模板的状态
     * 2. 修改定时任务信息(如果存在)
     *
     * @param messageTemplate
     */
    private void resetStatus(MessageTemplate messageTemplate) {
        messageTemplate.setUpdator(messageTemplate.getUpdator());
        messageTemplate.setMsgStatus(MessageStatus.INIT.getCode());
        messageTemplate.setAuditStatus(AuditStatus.WAIT_AUDIT.getCode());

        // 从数据库查询并注入 定时任务 ID
        MessageTemplate dbMsg = getById(messageTemplate.getId());
        if (Objects.nonNull(dbMsg) && Objects.nonNull(dbMsg.getCronTaskId())) {
            messageTemplate.setCronTaskId(dbMsg.getCronTaskId());
        }

        if (Objects.nonNull(messageTemplate.getCronTaskId())) {
            XxlJobInfo xxlJobInfo = xxlJobUtil.buildXxlJobInfo(messageTemplate);
            xxlJobService.saveCronTask(xxlJobInfo);
            xxlJobService.stopCronTask(messageTemplate.getCronTaskId());
        }
    }
}
