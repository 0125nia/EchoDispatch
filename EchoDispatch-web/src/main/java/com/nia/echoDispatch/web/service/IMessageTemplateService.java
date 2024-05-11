package com.nia.echoDispatch.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nia.echoDispatch.support.domain.MessageTemplate;

import java.util.List;

public interface IMessageTemplateService extends IService<MessageTemplate> {
    /**
     * 查询列表（分页）
     * @param deleted 删除 - 0：未删除  1：删除
     * @param page 分页对象
     * @return
     */
    List<MessageTemplate> findAllByUpdatedDesc(Integer deleted, Page<MessageTemplate> page);

    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);

}
