package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
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
    public String login(Admin admin,String num, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute("code");
        if (num.trim().equalsIgnoreCase(code)) {
            Admin admin1 = adminService.findByUserNameAndPassword(admin);
            if (admin1 != null) {
                request.getSession().setAttribute("admin", admin.getUsername());
                return null;
            }
            return "用户名密码错误";

        } else {
            return "验证码错误";
        }

    }
}
