package com.hong.dispatch.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/03/29 16:16
 **/
@Data
public class OrderDispatch implements Serializable {

    private static final long serialVersionUID = 9111357402963030257L;

    private Long id;
    private Long orderId;
    private String dispatchStatus;
    private String dispatchContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
