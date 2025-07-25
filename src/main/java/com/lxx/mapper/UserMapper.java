package com.lxx.mapper;

import com.lxx.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    @Select("select user_id, username, password from user where username = #{username}")
    User findByUsername(String username);
    
    /**
     * 注册用户
     * @param user 用户对象
     * @return 影响行数
     */
    @Insert("insert into user(username, password) values(#{username}, #{password})")
    int insert(User user);
    
    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    @Select("select user_id, username, password from user where user_id = #{userId}")
    User findByUserId(Integer userId);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    @Select("select user_id, username, password from user order by user_id desc")
    List<User> findAll();
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 影响行数
     */
    @Delete("delete from user where user_id = #{userId}")
    int delete(Integer userId);
} 