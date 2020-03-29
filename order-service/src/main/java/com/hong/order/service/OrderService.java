package com.hong.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hong.order.entity.MessageLog;
import com.hong.order.entity.Order;
import com.hong.order.mapper.MessageLogMapper;
import com.hong.order.mapper.OrderMapper;
import com.hong.order.util.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/03/29 16:49
 **/
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MessageLogMapper messageLogMapper;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Transactional
    public void createOrder(Order order) {
        // 1.插入订单数据
        orderMapper.insert(order);
        // 2.插入消息记录表数据
        MessageLog msgLog = new MessageLog();
        msgLog.setMsgId(GUIDGenerator.genGUID());
        // 保存消息整体 转为JSON 格式存储入库
        msgLog.setMsgContent(JSON.toJSONString(order));
        // 设置消息状态为0 表示发送中
        msgLog.setMsgStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        LocalDateTime date = LocalDateTime.now();
        msgLog.setNextRetry(date .plusMinutes(1));
        msgLog.setBusinessKey("order_create");
        msgLog.setCreateTime(date);
        messageLogMapper.insert(msgLog);
        // 3.发送消息到MQ
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(order));
        jsonObject.put("msgId",msgLog.getMsgId());
        rabbitMqSender.sendMsg(jsonObject);
    }

}
