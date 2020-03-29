package com.hong.order.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hong.order.entity.MessageLog;
import com.hong.order.entity.Order;
import com.hong.order.mapper.MessageLogMapper;
import com.hong.order.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wanghong
 * @date 2020/03/29 18:13
 *  兜底方案-- 定时任务检查 长时间没有发送成功的消息，然后重试
 **/
@Component
public class RetryMessageTask {

    @Autowired
    private MessageLogMapper messageLogMapper;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void reSend(){
        System.out.println("-----------定时重试失败消息开始-----------");
        List<MessageLog> list = messageLogMapper.queryResendMessage();
        list.forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3){
                messageLogMapper.updateStatus(messageLog.getMsgId(),"2");
            }else {
                messageLogMapper.updateResendCount(messageLog.getMsgId());
                Order order = JSON.parseObject(messageLog.getMsgContent(), Order.class);
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(order));
                jsonObject.put("msgId",messageLog.getMsgId());
                try {
                    rabbitMqSender.sendMsg(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 如果持续异常，不是系统自动能够解决的，需要监控预警，人工干预
                }
            }
        });
    }

}
