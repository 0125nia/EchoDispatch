package com.nia.echoDispatch.support;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            TestClass t1 = TestClass.builder().id("1").info("TestClass-t1-info").build();
            TestClass t2 = TestClass.builder().id("2").info("TestClass-t2-info").build();
            List<TestClass> list = Arrays.asList(t1, t2);
            if (CollUtil.isNotEmpty(list)){
                System.out.println(JSON.toJSONString(list));
            }
        }catch (Exception e){
            //使用guava
            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class TestClass{
    String id;
    String info;
}
