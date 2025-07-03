package com.lxx.pojo;

import lombok.Data;

@Data
public class Admin {
    private Integer adminId;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String createTime;
    private String lastLoginTime;
    private Integer status; // 0禁用 1启用
} 