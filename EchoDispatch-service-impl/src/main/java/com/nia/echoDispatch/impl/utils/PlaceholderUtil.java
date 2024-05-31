package com.nia.echoDispatch.impl.utils;

import java.util.Map;

/**
 * @author nia
 * @description 占位符替换工具类
 * @Date 2024/5/24
 */
public class PlaceholderUtil {
    /**
     * 前占位符
     */
    private static final String PRE_PLACE_HOLDER = "${";
    /**
     * 后占位符
     */
    private static final String AFTER_PLACE_HOLDER = "}";

    /**
     * 替换占位符
     * @param template 模板
     * @param variables 参数
     * @return 替换参数后的结果
     */
    public static String replacePlaceHolder(String template, Map<String,String> variables){
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String replace = PRE_PLACE_HOLDER + entry.getKey() + AFTER_PLACE_HOLDER;
            template = template.replace(replace, entry.getValue());
        }
        return template;
    }

}
