package com.nia.echoDispatch.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nia.echoDispatch.service.ISmsRecordService;
import com.nia.echoDispatch.support.domain.SmsRecord;
import com.nia.echoDispatch.support.mapper.SmsRecordMapper;
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
