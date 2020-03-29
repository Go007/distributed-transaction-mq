package com.hong.order.mapper;

import com.hong.order.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wanghong
 * @date 2020/03/29 16:24
 **/
@Repository
public interface OrderMapper {
    int insert(Order order);
    Order selectById(@Param("orderId") Long orderId);
    int updateStatus(@Param("orderId") Long orderId, @Param("orderStatus")String orderStatus);
}
