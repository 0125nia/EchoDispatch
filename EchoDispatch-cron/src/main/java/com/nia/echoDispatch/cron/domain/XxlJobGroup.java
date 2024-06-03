package com.nia.echoDispatch.cron.domain;

import cn.hutool.core.text.StrPool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author nia
 * @description
 * @Date 2024/6/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XxlJobGroup {

    private int id;
    private String appname;

    private String title;
    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private int addressType;

    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     */
    private String addressList;
    private Date updateTime;

    /**
     * registry list 执行器地址列表(系统注册)
     */
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (Objects.nonNull(addressList) && addressList.trim().length() > 0) {
            registryList = new ArrayList<>(Arrays.asList(addressList.split(StrPool.COMMA)));
        }
        return registryList;
    }
}
