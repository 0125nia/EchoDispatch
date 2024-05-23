package com.nia.echoDispatch.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.Processor;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author nia
 * @description 责任链：后置参数校验
 * @Date 2024/5/14
 */
@Service
public class AfterParamCheckProcessor implements Processor {

    /**
     * 邮件和手机号正则
     */
    public static final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    @Override
    public void process(ProcessContext processContext) {
        List<TaskInfo> taskInfos = processContext.getTaskInfos();
        if (CollUtil.isEmpty(taskInfos)) {
            processContext.setResult(BaseResultVO.fail(RespStatus.TASK_INFO_IS_NULL));
            processContext.setBreakFlag(true);
            return;
        }

        Iterator<TaskInfo> iterator = taskInfos.iterator();

        while (iterator.hasNext()) {
            TaskInfo taskInfo = iterator.next();
            Set<String> receiver = taskInfo.getReceiver();

            List<String> invalidReceivers = new ArrayList<>();
            for (String s : receiver) {
                if (!s.matches(PHONE_REGEX) && !s.matches(EMAIL_REGEX)){
                    invalidReceivers.add(s);
                }
            }
            //移除无效接收者
            if (CollUtil.isNotEmpty(invalidReceivers)){
                invalidReceivers.forEach(taskInfo.getReceiver()::remove);
            }

            if (CollUtil.isEmpty(receiver)) {
                iterator.remove();
            }
        }

        if (CollUtil.isEmpty(taskInfos)) {
            processContext.setResult(BaseResultVO.fail(RespStatus.ALL_RECEIVER_IS_INVALID));
            processContext.setBreakFlag(true);
        }


    }
}
