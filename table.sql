 CREATE TABLE IF NOT EXISTS `t_order` (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单号',
  user_id BIGINT(20) NOT NULL COMMENT '用户ID',
  order_status VARCHAR(10) DEFAULT NULL COMMENT '订单状态:1-支付中,2-支付成功,3-取消订单',
  order_content VARCHAR(256) NOT NULL COMMENT '订单内容(如买的商品,送货地址等)',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '订单信息表';

CREATE TABLE IF NOT EXISTS t_order_dispatch(
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '运单号',
  order_id BIGINT(20) NOT NULL COMMENT '订单号',
  dispatch_status VARCHAR(10) DEFAULT NULL COMMENT '调度状态',
  dispatch_content VARCHAR(256) DEFAULT NULL COMMENT '调度内容(送餐员，路线)',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '运单调度信息表';


CREATE TABLE IF NOT EXISTS t_message_log(
  msg_id  VARCHAR(32) NOT NULL COMMENT '消息唯一编号',
  msg_content VARCHAR(256) DEFAULT NULL COMMENT '消息内容',
  msg_status VARCHAR(10) DEFAULT NULL COMMENT '消息投递状态:0-投递中,1,投递成功,2-投递失败',
  try_count INT(4) DEFAULT 0 COMMENT '消息发送失败时的累计重试次数',
  next_retry DATETIME NOT NULL COMMENT '下一次重试时间 或 超时时间',
  business_key VARCHAR(32) DEFAULT NULL COMMENT '业务标识(可以记录哪个服务或者具体的url产生的消息)',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY(msg_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '本地消息日志表(当用于分布式事务时,需要和生产者端表在同一个schema下)';

