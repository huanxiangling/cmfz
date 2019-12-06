package com.baizhi.service;


import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest request;
    @Override
    public Map save(User user) {
        Map map = new HashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id);
        userDao.insert(user);
        map.put("userId", id);
        map.put("status", 200);
        // 查询数据 list
        // 把list集合转换为 json字符串
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-0ab7df07318e43d280ba3eee38311c5b");
        goEasy.publish("cmfz", "success");//content : json字符串
        return map;
    }

    @Override
    public Map findByCurrentPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<User> users = userDao.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));
        int records = userDao.selectCount(new User());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", users);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }

    @Override
    public Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<User> users = userDao.findAllSearch(searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = userDao.findTotalCountsSearch(searchField, searchString, searchOper);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", users);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }

    @Override
    public Map update(User user) {
        Map map = new HashMap();
        user.setPhoto(null);
        userDao.updateByPrimaryKeySelective(user);
        map.put("userId", user.getId());
        map.put("status", 200);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-0ab7df07318e43d280ba3eee38311c5b");
        goEasy.publish("cmfz", "success");//content : json字符串
        return map;
    }
    @Override
    public Map userMsg(User user) {
        Map map = new HashMap();
        int i = userDao.updateByPrimaryKeySelective(user);
        System.out.println(i);
        System.out.println(i == 1);
        if (i == 1) {
            map.put("status", 200);
            map.put("user", user);
        } else {
            map.put("status", -200);
            map.put("message", "信息补充失败");
        }
        return map;
    }

    @Override
    public Map deleteList(List<String> list) {
        Map map = new HashMap();
        userDao.deleteByIdList(list);
        map.put("status", 200);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-0ab7df07318e43d280ba3eee38311c5b");
        goEasy.publish("cmfz", "success");//content : json字符串
        return map;
    }

    @Override
    public Map findOne(User user) {
        Map map = new HashMap();
        User user1 = userDao.selectOne(user);
        if (user1 != null) {
            map.put("status", 200);
            map.put("user", user1);
        } else {
            map.put("status", -200);
            map.put("message", "账号密码错误");
        }
        return map;
    }
}
