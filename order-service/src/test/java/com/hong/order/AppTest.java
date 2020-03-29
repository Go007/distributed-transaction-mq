package com.hong.order;

import com.hong.order.entity.Order;
import com.hong.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest{

    @Autowired
    private OrderService orderService;

    @Test
    public void test(){
        Order order = new Order();
        order.setUserId(1001L);
        order.setOrderStatus("1");
        order.setOrderContent("生成订单");
        orderService.createOrder(order);
    }

}
