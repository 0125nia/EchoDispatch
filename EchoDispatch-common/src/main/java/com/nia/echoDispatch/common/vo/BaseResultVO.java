package com.nia.echoDispatch.common.vo;

import com.nia.echoDispatch.common.enums.RespStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author nia
 * @description
 * @Date 2024/5/14
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseResultVO<T> {
    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public BaseResultVO(String status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    public BaseResultVO(RespStatus status, String msg, T data) {
        this(status.getCode(),msg);
        this.data = data;
    }
    public BaseResultVO(RespStatus status,String msg){
        this(status.getCode(),msg);
    }
    public BaseResultVO(RespStatus status) {
        this(status, null);
    }


    /**
     * 自定义状态的响应
     *
     * @param status 状态
     * @return 自定义状态的响应
     */
    public static <T> BaseResultVO<T> resp(RespStatus status,String msg) {
        return new BaseResultVO<>(status,msg);
    }

    /**
     * @return 默认成功响应
     */
    public static BaseResultVO<Void> success() {
        return resp(RespStatus.SUCCESS, RespStatus.SUCCESS.getMsg());
    }


    /**
     * 默认失败响应
     */
    public static <T> BaseResultVO<T> fail() {
        return resp(RespStatus.FAIL, RespStatus.FAIL.getMsg());
    }

    /**
     * 自定义类型失败响应
     */
    public static <T> BaseResultVO<T> fail(RespStatus status,String msg){
        return resp(status,msg);
    }
}
