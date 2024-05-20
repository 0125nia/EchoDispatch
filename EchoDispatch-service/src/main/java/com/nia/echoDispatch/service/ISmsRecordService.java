package com.nia.echoDispatch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nia.echoDispatch.support.domain.SmsRecord;

import java.util.List;

public interface ISmsRecordService extends IService<SmsRecord> {

    /**
     * 通过日期和手机号找到发送记录
     *
     * @param phone
     * @param sendDate
     * @return
     */
    List<SmsRecord> findByPhoneAndSendDate(Long phone, Integer sendDate);
}
