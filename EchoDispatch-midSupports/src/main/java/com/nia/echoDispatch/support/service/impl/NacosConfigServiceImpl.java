package com.nia.echoDispatch.support.service.impl;

import cn.hutool.setting.dialect.Props;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.nia.echoDispatch.support.constants.PropertyConstants;
import com.nia.echoDispatch.support.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

/**
 * @author nia
 * @description 读取配置实现类
 * @Date 2024/5/29
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "EchoDispatch.property.name", havingValue = PropertyConstants.NACOS)
public class NacosConfigServiceImpl implements ConfigService {

    @Value("${EchoDispatch.property.name}")
    private String propertyName;

    @Value("${EchoDispatch.property.data-id}")
    private String nacosDataId;

    @Value("${EchoDispatch.property.group}")
    private String nacosGroup;

    /**
     * 本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);

    @NacosInjected
    private com.alibaba.nacos.api.config.ConfigService configService;
    /**
     * nacos配置
     */
    @Value("${austin.nacos.enabled}")
    private Boolean enableNacos;

    private final Properties properties = new Properties();

    @Override
    public String getProperty(String key,String defaultValue) {
        if (Boolean.TRUE.equals(enableNacos)) {
            try {
                String config = configService.getConfig(nacosDataId, nacosGroup, PropertyConstants.TIMEOUT);
                System.out.println(config);
                if (StringUtils.hasText(config)) {
                    properties.load(new StringReader(config));
                }
            }catch (Exception e){
                log.error("property ：{} is exception",propertyName);
//            throw new RuntimeException(e);
            }

            Optional<String> property = Optional.ofNullable(properties.getProperty(key));

            return property.orElse(null);
        }
        return props.getProperty(key, defaultValue);
    }
}
