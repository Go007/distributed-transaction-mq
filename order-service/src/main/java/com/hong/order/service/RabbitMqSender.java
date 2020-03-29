package com.hong.order.service;

import com.alibaba.fastjson.JSONObject;
import com.hong.order.mapper.MessageLogMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wanghong
 * @date 2020/03/29 17:38
 **/
@Component
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageLogMapper messageLogMapper;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     * <p>
     * 设置回调函数：confirm确认
     */
    @PostConstruct
    public void setConfirmCallback() {
        /**
         * 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，
         * 确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("生产端收到回执 delivery tag:" + correlationData);
            String msgId = correlationData.getId();
            if (ack) {
                // 消息发送成功
                int flag = messageLogMapper.updateStatus(msgId, "1");
                if (flag != 1){
                    System.out.println("警告:本地消息表的状态更新失败");
                }
            } else {
                // 发送失败，进行具体的后续操作:重试 或者 定时任务补偿等手段
                System.err.println("警告:本地消息表的状态更新出现异常,异常处理...");
            }
        });
    }

    public void sendMsg(JSONObject jsonObject) {
        // 消息唯一ID, CorrelationData 当收到消息回执时，会附带上这个参数
        CorrelationData correlationData = new CorrelationData(jsonObject.getString("msgId"));
        // @PostConstruct会在初始化时设置MQ回调处理函数
        rabbitTemplate.convertAndSend("order.exchange", "order.rk", jsonObject.toJSONString(), correlationData);
    }
}
