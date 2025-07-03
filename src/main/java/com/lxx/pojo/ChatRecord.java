package com.lxx.pojo;

import lombok.Data;
import java.util.Date;

/**
 * 聊天记录实体类
 */
@Data
public class ChatRecord {
    /**
     * 聊天记录ID
     */
    private Integer id;
    
    /**
     * 发送者用户名
     */
    private String fromName;
    
    /**
     * 接收者用户名
     */
    private String toName;
    
    /**
     * 消息内容
     */
    private String message;
    
    /**
     * 发送时间
     */
    private Date sendTime;
    
    /**
     * 消息类型（0:文本消息, 1:图片消息, 2:表情消息）
     */
    private Integer messageType;
    
    /**
     * 是否已读（0:未读, 1:已读）
     */
    private Integer isRead;
} 