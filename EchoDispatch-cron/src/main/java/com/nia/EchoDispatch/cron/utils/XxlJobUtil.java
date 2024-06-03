package com.nia.EchoDispatch.cron.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.nia.EchoDispatch.cron.constant.XxlJobConstants;
import com.nia.EchoDispatch.cron.domain.XxlJobGroup;
import com.nia.EchoDispatch.cron.domain.XxlJobInfo;
import com.nia.EchoDispatch.cron.enums.ExecutorRouteEnum;
import com.nia.EchoDispatch.cron.enums.ScheduleExpirationEnum;
import com.nia.EchoDispatch.cron.enums.ScheduleTypeEnum;
import com.nia.EchoDispatch.cron.service.XxlJobService;
import com.nia.echoDispatch.common.constant.BasicConstant;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author nia
 * @description
 * @Date 2024/6/3
 */
@Component
public class XxlJobUtil {

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.jobHandlerName}")
    private String jobHandlerName;

    @Autowired
    private XxlJobService xxlJobService;


    /**
     * 构建xxlJobInfo信息
     *
     * @param messageTemplate
     * @return
     */
    public XxlJobInfo buildXxlJobInfo(MessageTemplate messageTemplate) {

        String scheduleConf = messageTemplate.getExpectPushTime();
        // 如果没有指定cron表达式，说明立即执行(给到xxl-job延迟5秒的cron表达式)
        if (messageTemplate.getExpectPushTime().equals(String.valueOf(BasicConstant.FALSE))) {
            scheduleConf = DateUtil.format(DateUtil.offsetSecond(new Date(), XxlJobConstants.DELAY_TIME), BasicConstant.CRON_FORMAT);
        }

        XxlJobInfo xxlJobInfo = XxlJobInfo.builder()
                .jobGroup(queryJobGroupId()).jobDesc(messageTemplate.getName())
                .author(messageTemplate.getCreator())
                .scheduleConf(scheduleConf)
                .scheduleType(ScheduleTypeEnum.CRON.name())
                .misfireStrategy(ScheduleExpirationEnum.DO_NOTHING.name())
                .executorRouteStrategy(ExecutorRouteEnum.CONSISTENT_HASH.name())
                .executorHandler(XxlJobConstants.JOB_HANDLER_NAME)
                .executorParam(String.valueOf(messageTemplate.getId()))
                .executorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name())
                .executorTimeout(XxlJobConstants.TIME_OUT)
                .executorFailRetryCount(XxlJobConstants.RETRY_COUNT)
                .glueType(GlueTypeEnum.BEAN.name())
                .triggerStatus(BasicConstant.FALSE)
                .glueRemark(CharSequenceUtil.EMPTY)
                .glueSource(CharSequenceUtil.EMPTY)
                .alarmEmail(CharSequenceUtil.EMPTY)
                .childJobId(CharSequenceUtil.EMPTY).build();

        if (Objects.nonNull(messageTemplate.getCronTaskId())) {
            xxlJobInfo.setId(messageTemplate.getCronTaskId());
        }
        return xxlJobInfo;
    }

    /**
     * 根据就配置文件的内容获取jobGroupId，没有则创建
     *
     * @return
     */
    private Integer queryJobGroupId() {
        BaseResultVO<Integer> basicResultVO = xxlJobService.getGroupId(appName, jobHandlerName);
        if (Objects.isNull(basicResultVO.getData())) {
            XxlJobGroup xxlJobGroup = XxlJobGroup.builder().appname(appName).title(jobHandlerName).addressType(BasicConstant.FALSE).build();
            if (RespStatus.SUCCESS.getCode().equals(xxlJobService.createGroup(xxlJobGroup).getCode())) {
                return (int) xxlJobService.getGroupId(appName, jobHandlerName).getData();
            }
        }
        return basicResultVO.getData();
    }

}
