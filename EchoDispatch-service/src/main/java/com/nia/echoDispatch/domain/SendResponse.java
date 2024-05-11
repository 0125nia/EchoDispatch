package com.nia.echoDispatch.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SendResponse {
    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;
}
