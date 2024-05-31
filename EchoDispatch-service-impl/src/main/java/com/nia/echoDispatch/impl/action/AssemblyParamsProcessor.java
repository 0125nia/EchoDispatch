package com.nia.echoDispatch.impl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nia.echoDispatch.common.domian.MessageParam;
import com.nia.echoDispatch.common.domian.SendRequester;
import com.nia.echoDispatch.common.domian.TaskInfo;
import com.nia.echoDispatch.common.model.ContentModel;
import com.nia.echoDispatch.common.enums.ChannelType;
import com.nia.echoDispatch.common.enums.RespStatus;
import com.nia.echoDispatch.common.pipeline.ProcessContext;
import com.nia.echoDispatch.common.pipeline.Processor;
import com.nia.echoDispatch.common.vo.BaseResultVO;
import com.nia.echoDispatch.impl.utils.BusinessUtil;
import com.nia.echoDispatch.impl.utils.PlaceholderUtil;
import com.nia.echoDispatch.support.domain.MessageTemplate;
import com.nia.echoDispatch.support.mapper.MessageTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author nia
 * @description 责任链：拼装参数
 * @Date 2024/5/20
 */
@Service
public class AssemblyParamsProcessor implements Processor {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 实现拼装参数逻辑
     *
     * @param processContext
     */
    @Override
    public void process(ProcessContext processContext) {
        SendRequester sendRequest = processContext.getSendRequest();
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(sendRequest.getMessageTemplateId());
        if (messageTemplate == null) {
            processContext.setResult(BaseResultVO.fail(RespStatus.TEMPLATE_ID_ERROR));
            processContext.setBreakFlag(true);
            return;
        }

        Set<String> receivers = getReceivers(sendRequest.getMessageParamList());
        ContentModel contentModel = getContentModel(CollUtil.getFirst(sendRequest.getMessageParamList()), messageTemplate);


        TaskInfo taskInfo = TaskInfo.builder()
                .messageTemplateId(messageTemplate.getId())
                .taskBusinessId(BusinessUtil.generateTaskBusinessId(messageTemplate))
                .receiver(receivers)
                .sendChannel(messageTemplate.getSendChannel())
                .contentModel(contentModel)
                .msgType(messageTemplate.getMsgType())
                .build();

        ArrayList<TaskInfo> taskInfos = CollUtil.newArrayList(taskInfo);
        processContext.setTaskInfos(taskInfos);

    }

    /**
     * 获取内容模板对象
     *
     * @param messageParam    消息参数
     * @param messageTemplate 消息模板
     * @return 内容模板
     */
    private ContentModel getContentModel(MessageParam messageParam, MessageTemplate messageTemplate) {
        final String FIELD_NAME_URL = "url";
        // 得到真正的ContentModel 类型
        Class<? extends ContentModel> contentModelClass = Objects.requireNonNull(getChannelByCode(messageTemplate.getSendChannel())).getContentModelClass();
        // 得到入参
        Map<String, String> variables = messageParam.getVariables();
        // 参数模板
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());
        // 通过反射 组装出 contentModel

        Field[] fields = ReflectUtil.getFields(contentModelClass);
        // new实例
        ContentModel contentModel = ReflectUtil.newInstance(contentModelClass);
        // 遍历成员变量
        for (Field field : fields) {
            String jsonObjectString = jsonObject.getString(field.getName());
            if (jsonObjectString.isBlank()||jsonObjectString.isEmpty()){
                continue;
            }
            String result = PlaceholderUtil.replacePlaceHolder(jsonObjectString, variables);
            Object o = JSONUtil.isTypeJSON(result) ? JSONUtil.toBean(result, field.getType()):result;
            ReflectUtil.setFieldValue(contentModel,field,o);
        }
        return contentModel;
    }

    /**
     * 获取接收者集合
     *
     * @param messageParamList 信息参数列表
     * @return 接收者集合
     */
    private Set<String> getReceivers(List<MessageParam> messageParamList) {
        Set<String> receivers = new HashSet<>();
        for (MessageParam messageParam : messageParamList) {
            receivers.add(messageParam.getReceiver());
        }
        return receivers;
    }

    /**
     * 根据code获取对应的ChannelType枚举类
     *
     * @param sendChannel channel标识
     * @return ChannelType枚举类
     */
    private static ChannelType getChannelByCode(Integer sendChannel) {
        for (ChannelType value : ChannelType.values()) {
            if (Objects.equals(value.getCode(), sendChannel)) {
                return value;
            }
        }
        return null;
    }


}
