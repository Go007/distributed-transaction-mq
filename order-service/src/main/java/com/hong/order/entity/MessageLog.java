package com.hong.order.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/03/29 16:21
 **/
@Data
public class MessageLog {
    private String msgId;
    private String msgContent;
    private String msgStatus;
    private String businessKey;
    private Integer tryCount;
    private LocalDateTime nextRetry;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
