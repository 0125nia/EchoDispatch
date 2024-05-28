package com.nia.echoDispatch.handler.pending;

import com.nia.echoDispatch.common.domian.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author nia
 * @description 对TaskInfo分线程处理
 * @Date 2024/5/22
 */
@Component
@Slf4j
@Data
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{

    private TaskInfo taskInfo;

    @Override
    public void run() {
    }
}
