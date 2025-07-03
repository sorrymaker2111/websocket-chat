package com.lxx.mapper;

import com.lxx.pojo.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    
    /**
     * 根据用户名查询管理员
     * @param username 用户名
     * @return 管理员对象
     */
    @Select("select * from admin where username = #{username}")
    Admin findByUsername(String username);
    
    /**
     * 添加管理员
     * @param admin 管理员对象
     * @return 影响行数
     */
    @Insert("insert into admin(username, password, real_name, email, phone, create_time, status) " +
            "values(#{username}, #{password}, #{realName}, #{email}, #{phone}, now(), #{status})")
    int insert(Admin admin);
    
    /**
     * 更新管理员信息
     * @param admin 管理员对象
     * @return 影响行数
     */
    @Update("update admin set password=#{password}, real_name=#{realName}, email=#{email}, " +
            "phone=#{phone}, status=#{status} where admin_id=#{adminId}")
    int update(Admin admin);
    
    /**
     * 更新最后登录时间
     * @param adminId 管理员ID
     * @return 影响行数
     */
    @Update("update admin set last_login_time=now() where admin_id=#{adminId}")
    int updateLoginTime(Integer adminId);
    
    /**
     * 删除管理员
     * @param adminId 管理员ID
     * @return 影响行数
     */
    @Delete("delete from admin where admin_id=#{adminId}")
    int delete(Integer adminId);
    
    /**
     * 查询所有管理员
     * @return 管理员列表
     */
    @Select("select * from admin order by admin_id desc")
    List<Admin> findAll();
    
    /**
     * 根据ID查询管理员
     * @param adminId 管理员ID
     * @return 管理员对象
     */
    @Select("select * from admin where admin_id=#{adminId}")
    Admin findById(Integer adminId);
} 