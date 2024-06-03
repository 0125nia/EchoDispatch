package com.nia.EchoDispatch.cron.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.nia.EchoDispatch.cron.constant.XxlJobConstants;
import com.nia.EchoDispatch.cron.domain.XxlJobGroup;
import com.nia.EchoDispatch.cron.domain.XxlJobInfo;
import com.nia.EchoDispatch.cron.service.XxlJobService;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author nia
 * @description
 * @Date 2024/6/3
 */
@Service
@Slf4j
public class XxlJobServiceImpl implements XxlJobService {

    @Value("${xxl.job.admin.username}")
    private String xxlUserName;

    @Value("${xxl.job.admin.password}")
    private String xxlPassword;

    @Value("${xxl.job.admin.addresses}")
    private String xxlAddresses;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 新增/修改 定时任务
     *
     * @param xxlJobInfo
     * @return 新增时返回任务Id，修改时无返回
     */
    @Override
    public BaseResultVO saveCronTask(XxlJobInfo xxlJobInfo) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobInfo), Map.class);
        String path = Objects.isNull(xxlJobInfo.getId()) ? xxlAddresses + XxlJobConstants.INSERT_URL
                : xxlAddresses + XxlJobConstants.UPDATE_URL;

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);

            // 插入时需要返回Id，而更新时不需要
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                if (path.contains(XxlJobConstants.INSERT_URL)) {
                    Integer taskId = Integer.parseInt(String.valueOf(returnT.getContent()));
                    return BaseResultVO.success(taskId);
                } else if (path.contains(XxlJobConstants.UPDATE_URL)) {
                    return BaseResultVO.success();
                }
            }
        } catch (Exception e) {
            log.error("CronTaskService#saveTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(xxlJobInfo), JSON.toJSONString(returnT));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_SAVE_ERROR, JSON.toJSONString(returnT));

    }

    /**
     * 删除定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    @Override
    public BaseResultVO deleteCronTask(Integer taskId) {
        String path = xxlAddresses + XxlJobConstants.DELETE_URL;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", taskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BaseResultVO.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#deleteCronTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_DELETE_ERROR, JSON.toJSONString(returnT));
    }
    /**
     * 启动定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    @Override
    public BaseResultVO startCronTask(Integer taskId) {
        String path = xxlAddresses + XxlJobConstants.RUN_URL;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", taskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BaseResultVO.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#startCronTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_START_ERROR, JSON.toJSONString(returnT));
    }


    /**
     * 暂停定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    @Override
    public BaseResultVO stopCronTask(Integer taskId) {
        String path = xxlAddresses + XxlJobConstants.STOP_URL;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", taskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BaseResultVO.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#stopCronTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_STOP_ERROR, JSON.toJSONString(returnT));
    }



    /**
     * 得到执行器Id
     *
     * @param appName
     * @param title
     * @return BasicResultVO
     */
    @Override
    public BaseResultVO getGroupId(String appName, String title) {
        String path = xxlAddresses + XxlJobConstants.JOB_GROUP_PAGE_LIST;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("appname", appName);
        params.put("title", title);

        HttpResponse response = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            if (Objects.isNull(response)) {
                return BaseResultVO.fail(RespStatus.XXL_JOB_GET_GROUP_ID);
            }
            Integer id = JSON.parseObject(response.body()).getJSONArray("data").getJSONObject(0).getInteger("id");
            if (response.isOk() && Objects.nonNull(id)) {
                return BaseResultVO.success(id);
            }
        } catch (Exception e) {
            log.error("CronTaskService#getGroupId fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(response.body()));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_GET_GROUP_ID, JSON.toJSONString(response.body()));
    }

    /**
     * 创建执行器
     *
     * @param xxlJobGroup
     * @return BasicResultVO
     */
    @Override
    public BaseResultVO createGroup(XxlJobGroup xxlJobGroup) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobGroup), Map.class);
        String path = xxlAddresses + XxlJobConstants.JOB_GROUP_INSERT_URL;

        HttpResponse response;
        ReturnT returnT = null;

        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BaseResultVO.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        invalidateCookie();
        return BaseResultVO.fail(RespStatus.XXL_JOB_CREATE_GROUP, JSON.toJSONString(returnT));
    }


    /**
     * 获取xxl cookie
     *
     * @return String
     */
    private String getCookie() {
        String cachedCookie = redisTemplate.opsForValue().get(XxlJobConstants.COOKIE_PREFIX + xxlUserName);
        if (StrUtil.isNotBlank(cachedCookie)) {
            return cachedCookie;
        }

        Map<String, Object> params = MapUtil.newHashMap();
        params.put("userName", xxlUserName);
        params.put("password", xxlPassword);
        params.put("randomCode", IdUtil.fastSimpleUUID());

        String path = xxlAddresses + XxlJobConstants.LOGIN_URL;
        HttpResponse response = null;
        try {
            response = HttpRequest.post(path).form(params).execute();
            if (response.isOk()) {
                List<HttpCookie> cookies = response.getCookies();
                StringBuilder sb = new StringBuilder();
                for (HttpCookie cookie : cookies) {
                    sb.append(cookie.toString());
                }
                redisTemplate.opsForValue().set(XxlJobConstants.COOKIE_PREFIX + xxlUserName, sb.toString());
                return sb.toString();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup getCookie,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(params), JSON.toJSONString(response));
        }
        return null;
    }
    /**
     *
     * 清除缓存的 cookie
     */
    private void invalidateCookie() {
        redisTemplate.delete(XxlJobConstants.COOKIE_PREFIX + xxlUserName);
    }
}
