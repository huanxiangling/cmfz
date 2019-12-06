package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map save(User user);

    Map findByCurrentPage(Integer page, Integer rows);

    Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

    Map update(User user);

    Map deleteList(List<String> list);

    Map findOne(User user);

    Map userMsg(User user);
}
