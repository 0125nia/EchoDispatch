package com.nia.echoDispatch.handler.pending;

import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.reject.RejectHandlerGetter;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.NamedThreadFactory;
import com.nia.echoDispatch.handler.pool.ThreadPoolConstants;
import com.nia.echoDispatch.handler.utils.GroupIdUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author nia
 * @description 延迟处理task，分配线程池
 * @Date 2024/5/22
 */
@Component
public class TaskPendingHolder {
    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdUtils.getAllGroupIds();

    /**
     * 存储线程池与groupId的映射
     */
    private Map<String, ExecutorService> holder = new HashMap<>(32);

    /**
     * 给每个渠道，每种类型初始化一个线程池
     */
    @PostConstruct
    public void init(){
        for (String groupId : groupIds) {
            DtpExecutor executor = getExecutor(groupId);
            holder.put(groupId,executor);
        }
    }


    public static DtpExecutor getExecutor(String groupId){
        return new DtpExecutor(
                ThreadPoolConstants.CORE_POOL_SIZE,
                ThreadPoolConstants.MAX_POOL_SIZE,
                ThreadPoolConstants.KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                QueueTypeEnum.buildBlockingQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), ThreadPoolConstants.WORK_QUEUE_SIZE,false),
                new NamedThreadFactory("dtp"),
                RejectHandlerGetter.buildRejectedHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                );
    }

    public Executor route(String groupId) {
        return holder.get(groupId);
    }
}
