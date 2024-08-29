package com.nia.echoDispatch.cron.utils;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nia
 * @description 文件读取工具类
 * @Date 2024/6/3
 */
@Slf4j
public class FileUtil {
    /**
     * csv文件 存储 接收者 的列名
     */
    public static final String RECEIVER_KEY = "userId";

    private FileUtil() {
    }

    /**
     * 从文件的每一行数据获取到params信息
     * [{key:value},{key:value}]
     *
     * @param fieldMap
     * @return
     */
    public static Map<String, String> getParamFromLine(Map<String, String> fieldMap) {
        HashMap<String, String> params = MapUtil.newHashMap();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            if (!RECEIVER_KEY.equals(entry.getKey())) {
                params.put(entry.getKey(), entry.getValue());
            }
        }

        return params;
    }

}

