package com.hong.dispatch.mapper;

import com.hong.dispatch.entity.OrderDispatch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wanghong
 * @date 2020/03/29 17:58
 **/
@Repository
public interface OrderDispatchMapper {

    int insert(OrderDispatch orderDispatch);

    OrderDispatch selectByOrderId(@Param("orderId") Long orderId);

    int updateStatusByOrderId(@Param("orderId") Long orderId,@Param("dispatchStatus") String dispatchStatus);
}
