package com.lxx.service.impl;

import com.lxx.mapper.ChatRecordMapper;
import com.lxx.pojo.ChatRecord;
import com.lxx.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天记录服务实现类
 */
@Service
public class ChatRecordServiceImpl implements ChatRecordService {

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Override
    public boolean saveChatRecord(ChatRecord chatRecord) {
        return chatRecordMapper.save(chatRecord) > 0;
    }

    @Override
    public List<ChatRecord> findChatRecordsByUsername(String username) {
        return chatRecordMapper.findByUsername(username);
    }

    @Override
    public List<ChatRecord> findChatRecordsByFromAndTo(String fromName, String toName) {
        return chatRecordMapper.findByFromAndTo(fromName, toName);
    }

    @Override
    public boolean markAsRead(String fromName, String toName) {
        return chatRecordMapper.markAsRead(fromName, toName) > 0;
    }

    @Override
    public boolean deleteChatRecord(Integer id) {
        return chatRecordMapper.delete(id) > 0;
    }

    @Override
    public boolean deleteUserChatRecords(String username) {
        return chatRecordMapper.deleteByUsername(username) > 0;
    }

    @Override
    public int countTodayMessages() {
        return chatRecordMapper.countTodayMessages();
    }
} 