package com.lxx.controller;

import com.lxx.pojo.Admin;
import com.lxx.pojo.Result;
import com.lxx.pojo.User;
import com.lxx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     *
     * @param admin   提交的管理员数据，包含用户名和密码
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin, HttpSession session) {
        Result result = new Result();
        
        // 调用Service进行登录验证
        Admin loginAdmin = adminService.login(admin.getUsername(), admin.getPassword());
        
        if (loginAdmin != null) {
            result.setFlag(true);
            // 将数据存储到session对象中
            session.setAttribute("admin", loginAdmin.getUsername());
            session.setAttribute("adminId", loginAdmin.getAdminId());
        } else {
            result.setFlag(false);
            result.setMessage("用户名或密码错误");
        }
        return result;
    }

    /**
     * 获取管理员名
     *
     * @param session
     * @return
     */
    @GetMapping("/getAdminName")
    public String getAdminName(HttpSession session) {
        String adminName = (String) session.getAttribute("admin");
        return adminName;
    }
    
    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpSession session) {
        Result result = new Result();
        session.removeAttribute("admin");
        session.removeAttribute("adminId");
        result.setFlag(true);
        return result;
    }
    
    /**
     * 获取所有管理员
     *
     * @return
     */
    @GetMapping("/getAllAdmins")
    public Result getAllAdmins() {
        Result result = new Result();
        List<Admin> admins = adminService.findAllAdmins();
        result.setFlag(true);
        result.setData(admins);
        return result;
    }
    
    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return
     */
    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        Result result = new Result();
        boolean success = adminService.addAdmin(admin);
        result.setFlag(success);
        if (success) {
            result.setMessage("添加成功");
        } else {
            result.setMessage("用户名已存在");
        }
        return result;
    }
    
    /**
     * 更新管理员
     *
     * @param admin 管理员信息
     * @return
     */
    @PutMapping("/updateAdmin")
    public Result updateAdmin(@RequestBody Admin admin) {
        Result result = new Result();
        boolean success = adminService.updateAdmin(admin);
        result.setFlag(success);
        if (success) {
            result.setMessage("更新成功");
        } else {
            result.setMessage("更新失败");
        }
        return result;
    }
    
    /**
     * 删除管理员
     *
     * @param adminId 管理员ID
     * @return
     */
    @DeleteMapping("/deleteAdmin/{adminId}")
    public Result deleteAdmin(@PathVariable Integer adminId) {
        Result result = new Result();
        boolean success = adminService.deleteAdmin(adminId);
        result.setFlag(success);
        if (success) {
            result.setMessage("删除成功");
        } else {
            result.setMessage("删除失败");
        }
        return result;
    }
    
    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/getAllUsers")
    public Result getAllUsers() {
        Result result = new Result();
        List<User> users = adminService.findAllUsers();
        result.setFlag(true);
        result.setData(users);
        return result;
    }
    
    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return
     */
    @DeleteMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        Result result = new Result();
        boolean success = adminService.deleteUser(userId);
        result.setFlag(success);
        if (success) {
            result.setMessage("删除成功");
        } else {
            result.setMessage("删除失败");
        }
        return result;
    }
    
    /**
     * 获取用户聊天记录
     *
     * @param username 用户名
     * @return
     */
    @GetMapping("/getUserChatHistory/{username}")
    public Result getUserChatHistory(@PathVariable String username) {
        Result result = new Result();
        List<Object> chatHistory = adminService.findUserChatHistory(username);
        result.setFlag(true);
        result.setData(chatHistory);
        return result;
    }
    
    /**
     * 获取今日消息数量
     *
     * @return
     */
    @GetMapping("/getTodayMessageCount")
    public Result getTodayMessageCount() {
        Result result = new Result();
        int count = adminService.getTodayMessageCount();
        result.setFlag(true);
        result.setData(count);
        return result;
    }
} 