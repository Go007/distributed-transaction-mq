package com.hong.order.mapper;

import com.hong.order.entity.MessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghong
 * @date 2020/03/29 16:51
 **/
@Repository
public interface MessageLogMapper {
    int insert(MessageLog messageLog);
    int updateStatus(@Param("msgId") String msgId,@Param("msgStatus") String msgStatus);
    int updateResendCount(@Param("msgId") String msgId);
    List<MessageLog> queryResendMessage();
}
