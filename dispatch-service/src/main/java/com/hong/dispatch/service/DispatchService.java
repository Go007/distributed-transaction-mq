package com.hong.dispatch.service;

import com.hong.dispatch.entity.OrderDispatch;
import com.hong.dispatch.mapper.OrderDispatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/03/29 13:53
 **/
@Service
public class DispatchService {

    @Autowired
    private OrderDispatchMapper orderDispatchMapper;

    public void dispatch(String orderNum){
        OrderDispatch orderDispatch = new OrderDispatch();
        orderDispatch.setOrderNum(orderNum);
        orderDispatch.setDispatchStatus("0");
        orderDispatch.setDispatchContent("订单生成,即将派送");
        LocalDateTime date = LocalDateTime.now();
        orderDispatch.setCreateTime(date);
        orderDispatch.setUpdateTime(date);
        orderDispatchMapper.insert(orderDispatch);
    }
}
