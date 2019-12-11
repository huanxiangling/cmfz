package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.Map;

public interface AdminService {
    Admin findByUserNameAndPassword(Admin admin);

    Admin findOne(String id);

    Map findAll(Integer page, Integer rows);

    Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

}
