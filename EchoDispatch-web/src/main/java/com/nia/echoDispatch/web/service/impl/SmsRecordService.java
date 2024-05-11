package com.nia.echoDispatch.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nia.echoDispatch.support.domain.SmsRecord;
import com.nia.echoDispatch.support.mapper.SmsRecordMapper;
import com.nia.echoDispatch.web.service.ISmsRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsRecordService extends ServiceImpl<SmsRecordMapper, SmsRecord> implements ISmsRecordService {
    @Override
    public List<SmsRecord> findByPhoneAndSendDate(Long phone, Integer sendDate) {
        QueryWrapper<SmsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone).eq("sendDate",sendDate);
        return baseMapper.selectList(queryWrapper);
    }
}
