package com.nia.echoDispatch.common.dto.impl;

import com.nia.echoDispatch.common.dto.ContentModel;
import lombok.*;

/**
 * @author nia
 * @description 邮件消息体
 * @Date 2024/5/23
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailContentModel extends ContentModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 邮件附件链接
     */
    private String url;
}
