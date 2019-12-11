package com.baizhi.controller;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.entity.Role;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
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

    @RequestMapping("change")
    public Map change(String oper, String[] id) {
        List list = new ArrayList<>();
        AdminRole adminRole = new AdminRole();
        //删除当前admin的role关系
        for (String s : id) {
            adminRole.setAdminId(s);
            adminRoleDao.delete(adminRole);
            list.add(s);
        }
        adminDao.deleteByIdList(list);
        Map map = new HashMap();
        map.put("status", 200);
        return map;
    }

    @RequestMapping("findOne")
    public Admin findOne(String id) {
        return adminService.findOne(id);
    }

    @RequestMapping("findRoles")
    public List<Role> findRoles() {
        return roleDao.selectAll();
    }

    @RequestMapping("save")
    public void save(Admin admin, String[] roless) {
        String adminId = UUID.randomUUID().toString();
        admin.setId(adminId);
        adminDao.insert(admin);
        AdminRole adminRole = new AdminRole();
        //建立新的adminRole关系
        for (String id : roless) {
            adminRole.setId(UUID.randomUUID().toString());
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(id);
            adminRoleDao.insert(adminRole);
        }
    }

    @RequestMapping("update")
    public void update(Admin admin, String[] roless) {
        adminDao.updateByPrimaryKeySelective(admin);
        AdminRole adminRole = new AdminRole();
        //删除当前admin的role关系
        String adminId = admin.getId();
        adminRole.setAdminId(adminId);
        adminRoleDao.delete(adminRole);
        //建立新的adminRole关系
        for (String id : roless) {
            adminRole.setId(UUID.randomUUID().toString());
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(id);
            adminRoleDao.insert(adminRole);
        }
    }

    @RequestMapping("showAllAdmin")
    public Map showAllAdmin(String searchField, String searchString, String searchOper, boolean _search, Integer page, Integer rows) {
        if (_search) {
            return adminService.findAllSearch(searchField, searchString, searchOper, page, rows);
        } else {
            return adminService.findAll(page, rows);
        }
    }
}
