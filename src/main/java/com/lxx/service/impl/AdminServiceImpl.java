package com.lxx.service.impl;

import com.lxx.mapper.AdminMapper;
import com.lxx.mapper.ChatRecordMapper;
import com.lxx.mapper.UserMapper;
import com.lxx.pojo.Admin;
import com.lxx.pojo.ChatRecord;
import com.lxx.pojo.User;
import com.lxx.service.AdminService;
import com.lxx.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ChatRecordMapper chatRecordMapper;
    
    @Autowired
    private ChatRecordService chatRecordService;
    
    @Override
    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && password.equals(admin.getPassword())) {
            // 更新最后登录时间
            adminMapper.updateLoginTime(admin.getAdminId());
            return admin;
        }
        return null;
    }
    
    @Override
    public boolean addAdmin(Admin admin) {
        // 判断用户名是否已存在
        Admin existAdmin = adminMapper.findByUsername(admin.getUsername());
        if (existAdmin != null) {
            return false;
        }
        
        // 设置默认状态为启用
        if (admin.getStatus() == null) {
            admin.setStatus(1);
        }
        
        // 添加管理员
        return adminMapper.insert(admin) > 0;
    }
    
    @Override
    public boolean updateAdmin(Admin admin) {
        return adminMapper.update(admin) > 0;
    }
    
    @Override
    public boolean deleteAdmin(Integer adminId) {
        return adminMapper.delete(adminId) > 0;
    }
    
    @Override
    public List<Admin> findAllAdmins() {
        return adminMapper.findAll();
    }
    
    @Override
    public Admin findAdminById(Integer adminId) {
        return adminMapper.findById(adminId);
    }
    
    @Override
    public List<User> findAllUsers() {
        return userMapper.findAll();
    }
    
    @Override
    public boolean deleteUser(Integer userId) {
        return userMapper.delete(userId) > 0;
    }
    
    @Override
    public List<Object> findUserChatHistory(String username) {
        // 查询与该用户相关的所有聊天记录
        List<ChatRecord> chatRecords = chatRecordMapper.findByUsername(username);
        return new ArrayList<>(chatRecords);
    }
    
    @Override
    public int getTodayMessageCount() {
        return chatRecordService.countTodayMessages();
    }
} 