package com.hong.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wanghong
 * @date 2020/03/29 18:28
 **/
@Component
public class OrderDispatchConsumer {

    @Autowired
    private DispatchService dispatchService;

    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order.queue",durable = "true"),
            exchange = @Exchange(name="order.exchange",durable = "true",type = "topic"),
            key = "order.*"
    )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
   // public void onOrderMessage(@Payload JSONObject order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
    public void onOrderMessage(@Payload JSONObject order, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel) throws IOException {
        try {
            //消费者操作
            System.out.println("---------收到消息，开始消费---------");
            String  orderNum = order.getString("orderNum");
            System.out.println("订单编号："+ orderNum);

            /**
             * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
             * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
             * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
             */
            // Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

            // 幂等性，根据业务去重，防止重复消费，思路：记住以往处理过的数据
            // 创建一条运单(最终一致),这里可以设计orderId unique_key，这样有重复orderId插入时，数据库层会报错
            dispatchService.dispatch(orderNum);

            /**
             *  multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
             */
            boolean multiple = false;

            //ACK,确认一条消息已经被消费
            channel.basicAck(deliveryTag,multiple);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(deliveryTag,false,true); //重发
            //channel.basicNack(deliveryTag,false,false); 不要了丢弃(死信队列)
        }

        // 如果不给回复，就等这个consumer断开连接后，MQ-server会继续推送
    }
}
