package com.nia.echoDispatch.common.dto.impl;

import com.nia.echoDispatch.common.dto.ContentModel;
import lombok.*;

/**
 * @author nia
 * @description 短信内容模型
 * @Date 2024/5/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsContentModel extends ContentModel {
    /**
     * 发送内容
     */
    private String content;
    /**
     * 链接
     */
    private String url;
}
