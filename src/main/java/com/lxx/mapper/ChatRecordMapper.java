package com.lxx.mapper;

import com.lxx.pojo.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天记录Mapper接口
 */
@Mapper
public interface ChatRecordMapper {
    
    /**
     * 保存聊天记录
     * @param chatRecord 聊天记录对象
     * @return 影响的行数
     */
    int save(ChatRecord chatRecord);
    
    /**
     * 查询用户的聊天记录
     * @param username 用户名
     * @return 聊天记录列表
     */
    List<ChatRecord> findByUsername(String username);
    
    /**
     * 查询两个用户之间的聊天记录
     * @param fromName 发送者用户名
     * @param toName 接收者用户名
     * @return 聊天记录列表
     */
    List<ChatRecord> findByFromAndTo(@Param("fromName") String fromName, @Param("toName") String toName);
    
    /**
     * 将消息标记为已读
     * @param fromName 发送者用户名
     * @param toName 接收者用户名
     * @return 影响的行数
     */
    int markAsRead(@Param("fromName") String fromName, @Param("toName") String toName);
    
    /**
     * 删除聊天记录
     * @param id 聊天记录ID
     * @return 影响的行数
     */
    int delete(Integer id);
    
    /**
     * 删除用户的所有聊天记录
     * @param username 用户名
     * @return 影响的行数
     */
    int deleteByUsername(String username);
    
    /**
     * 获取今日消息数量
     * @return 今日消息数量
     */
    int countTodayMessages();
} 