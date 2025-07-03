package com.lxx.service;

import com.lxx.pojo.ChatRecord;

import java.util.List;

/**
 * 聊天记录服务接口
 */
public interface ChatRecordService {
    
    /**
     * 保存聊天记录
     * @param chatRecord 聊天记录对象
     * @return 是否保存成功
     */
    boolean saveChatRecord(ChatRecord chatRecord);
    
    /**
     * 查询用户的聊天记录
     * @param username 用户名
     * @return 聊天记录列表
     */
    List<ChatRecord> findChatRecordsByUsername(String username);
    
    /**
     * 查询两个用户之间的聊天记录
     * @param fromName 发送者用户名
     * @param toName 接收者用户名
     * @return 聊天记录列表
     */
    List<ChatRecord> findChatRecordsByFromAndTo(String fromName, String toName);
    
    /**
     * 将消息标记为已读
     * @param fromName 发送者用户名
     * @param toName 接收者用户名
     * @return 是否标记成功
     */
    boolean markAsRead(String fromName, String toName);
    
    /**
     * 删除聊天记录
     * @param id 聊天记录ID
     * @return 是否删除成功
     */
    boolean deleteChatRecord(Integer id);
    
    /**
     * 删除用户的所有聊天记录
     * @param username 用户名
     * @return 是否删除成功
     */
    boolean deleteUserChatRecords(String username);
    
    /**
     * 获取今日消息数量
     * @return 今日消息数量
     */
    int countTodayMessages();
} 