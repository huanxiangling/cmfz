package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("login")
    public String login(Admin admin, String num, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("code");
        if (num.trim().equalsIgnoreCase(code)) {
            //1.封装令牌
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
            //2.通过util类获取subject主体
            Subject subject = SecurityUtils.getSubject();
            //3.登录
            try {
                subject.login(usernamePasswordToken);
                return null;
            } catch (Exception e) {
                return "用户信息错误";
            }


        } else {
            return "验证码错误";
        }

    }

    @RequestMapping("loginOut")
    public void loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
