package com.lxx.controller;


import com.lxx.pojo.Result;
import com.lxx.pojo.User;
import com.lxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登陆
     *
     * @param user    提交的用户数据，包含用户名和密码
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        Result result = new Result();
        
        // 调用Service进行登录验证
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        
        if (loginUser != null) {
            result.setFlag(true);
            // 将数据存储到session对象中
            session.setAttribute("user", loginUser.getUsername());
            session.setAttribute("userId", loginUser.getUserId());
        } else {
            result.setFlag(false);
            result.setMessage("用户名或密码错误");
        }
        return result;
    }

    /**
     * 获取用户名
     *
     * @param session
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {
        String username = (String) session.getAttribute("user");
        return username;
    }
    
    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result = new Result();
        
        // 判断用户名和密码是否为空
        if (user.getUsername() == null || user.getPassword() == null) {
            result.setFlag(false);
            result.setMessage("用户名或密码不能为空");
            return result;
        }
        
        // 调用Service进行注册
        boolean registerResult = userService.register(user);
        
        if (registerResult) {
            result.setFlag(true);
            result.setMessage("注册成功");
        } else {
            result.setFlag(false);
            result.setMessage("用户名已存在");
        }
        
        return result;
    }
}
