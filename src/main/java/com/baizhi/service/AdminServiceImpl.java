package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin findByUserNameAndPassword(Admin admin) {
        return adminDao.selectOne(admin);
    }

    @Override
    public Admin findOne(String id) {
        return adminDao.findById(id);
    }

    @Override
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Admin> admins = adminDao.findAll((page - 1) * rows, rows);
        int records = adminDao.selectCount(new Admin());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", admins);
        map.put("records", records);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    public Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        Map map = new HashMap();
        if (searchField.equals("roles")) {
            searchField = "c." + "name";
        } else {
            searchField = "a." + searchField;
        }
        List<Admin> admins = adminDao.findAllSearch(searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = adminDao.findTotalCountsSearch(searchField, searchString, searchOper);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", admins);
        map.put("records", records);
        map.put("total", total);
        map.put("page", page);
        return map;
    }
}
