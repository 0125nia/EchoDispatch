package com.nia.EchoDispatch.cron.service;

import com.nia.EchoDispatch.cron.domain.XxlJobGroup;
import com.nia.EchoDispatch.cron.domain.XxlJobInfo;
import com.nia.echoDispatch.common.vo.BaseResultVO;

/**
 * @author nia
 * @description
 * @Date 2024/6/3
 */
public interface XxlJobService {

    /**
     * 新增/修改 定时任务
     *
     * @param xxlJobInfo
     * @return 新增时返回任务Id，修改时无返回
     */
    BaseResultVO saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 删除定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BaseResultVO deleteCronTask(Integer taskId);

    /**
     * 启动定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BaseResultVO startCronTask(Integer taskId);


    /**
     * 暂停定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BaseResultVO stopCronTask(Integer taskId);


    /**
     * 得到执行器Id
     *
     * @param appName
     * @param title
     * @return BasicResultVO
     */
    BaseResultVO getGroupId(String appName, String title);

    /**
     * 创建执行器
     *
     * @param xxlJobGroup
     * @return BasicResultVO
     */
    BaseResultVO createGroup(XxlJobGroup xxlJobGroup);
}
