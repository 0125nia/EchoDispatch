package com.nia.echoDispatch.support.service;

/**
 * @author nia
 * @description 读取配置服务
 * @Date 2024/5/29
 */
public interface NacosService {

    /**
     * 获取远程配置
     * @param key
     * @return
     */
    String getProperty(String key);

}
