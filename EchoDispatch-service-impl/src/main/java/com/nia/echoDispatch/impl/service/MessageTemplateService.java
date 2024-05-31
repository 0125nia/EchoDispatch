package com.nia.echoDispatch.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nia.echoDispatch.common.constant.DataConstants;
import com.nia.echoDispatch.service.IMessageTemplateService;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.mapper.MessageTemplateMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageTemplateService extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {
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
}
