package com.nia.echoDispatch.support.service.impl;

import cn.hutool.core.text.StrPool;
import cn.hutool.setting.dialect.Props;
import com.ctrip.framework.apollo.Config;
import com.nia.echoDispatch.support.constants.PropertyConstants;
import com.nia.echoDispatch.support.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author nia
 * @description
 * @Date 2024/9/2
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "EchoDispatch.property.name", havingValue = PropertyConstants.APOLLO)
public class ApolloConfigServiceImpl implements ConfigService {

    /**
     * 本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);
    /**
     * apollo配置
     */
    @Value("${apollo.bootstrap.enabled}")
    private Boolean enableApollo;
    @Value("${apollo.bootstrap.namespaces}")
    private String namespaces;

    @Override
    public String getProperty(String key,String defaultValue) {
        if (Boolean.TRUE.equals(enableApollo)) {
            Config config = com.ctrip.framework.apollo.ConfigService.getConfig(namespaces.split(StrPool.COMMA)[0]);
            return config.getProperty(key, defaultValue);
        }
        return props.getProperty(key, defaultValue);
    }
}
