package com.nia.echoDispatch.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nia.echoDispatch.support.domain.SmsRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsRecordMapper extends BaseMapper<SmsRecord> { }