package com.hong.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/03/29 16:13
 **/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 9111357402963030257L;

    private Long id;
    private String orderNum;
    private Long userId;
    private String orderStatus;
    private String orderContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
