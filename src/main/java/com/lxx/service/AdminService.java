package com.lxx.service;

import com.lxx.pojo.Admin;
import com.lxx.pojo.User;

import java.util.List;

public interface AdminService {
    
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 管理员对象，登录失败返回null
     */
    Admin login(String username, String password);
    
    /**
     * 添加管理员
     * @param admin 管理员对象
     * @return 是否添加成功
     */
    boolean addAdmin(Admin admin);
    
    /**
     * 更新管理员信息
     * @param admin 管理员对象
     * @return 是否更新成功
     */
    boolean updateAdmin(Admin admin);
    
    /**
     * 删除管理员
     * @param adminId 管理员ID
     * @return 是否删除成功
     */
    boolean deleteAdmin(Integer adminId);
    
    /**
     * 查询所有管理员
     * @return 管理员列表
     */
    List<Admin> findAllAdmins();
    
    /**
     * 根据ID查询管理员
     * @param adminId 管理员ID
     * @return 管理员对象
     */
    Admin findAdminById(Integer adminId);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> findAllUsers();
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Integer userId);
    
    /**
     * 查询指定用户的聊天记录
     * @param username 用户名
     * @return 聊天记录列表
     */
    List<Object> findUserChatHistory(String username);
} 