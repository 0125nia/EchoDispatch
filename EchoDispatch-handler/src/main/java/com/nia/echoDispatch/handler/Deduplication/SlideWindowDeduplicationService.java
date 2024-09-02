package com.nia.echoDispatch.handler.Deduplication;

import cn.hutool.core.util.IdUtil;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.support.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nia
 * @description 滑动窗口去重，基于redis中zset的滑动窗口去重 由lua脚本实现
 * @Date 2024/8/29
 */
@Service
@Component
public class SlideWindowDeduplicationService {
    private static final String LIMIT_TAG = "SW_";

    @Autowired
    private RedisUtil redisUtil;
    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    }

    public Set<String> filter(TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());
        long nowTime = System.currentTimeMillis();
        for (String receiver : taskInfo.getReceiver()) {
            String key = LIMIT_TAG + generateDeduplicationKey(taskInfo, receiver);
            String scoreValue = String.valueOf(IdUtil.getSnowflake().nextId());
            String score = String.valueOf(nowTime);

            final Boolean result = redisUtil.execLimitLua(redisScript, Collections.singletonList(key),
                    String.valueOf(param.getDeduplicationTime() * 1000), score, scoreValue);
            if (Boolean.TRUE.equals(result)) {
                filterReceiver.add(receiver);
            }

        }
        return filterReceiver;
    }

    private String generateDeduplicationKey(TaskInfo taskInfo, String receiver) {
        return LIMIT_TAG + receiver + taskInfo.getSendChannel();
    }
}
