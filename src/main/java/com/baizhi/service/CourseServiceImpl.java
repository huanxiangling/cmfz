package com.baizhi.service;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public Map findOne(Course course) {
        Map map = new HashMap();
        Course course1 = courseDao.selectOne(course);
        map.put("status", 200);
        map.put("option", course1);
        return map;
    }

    @Override
    public Map findAll(String uid) {
        Map map = new HashMap();
        Course course = new Course();
        course.setUserId(uid);
        List<Course> courses = courseDao.select(course);
        map.put("status", 200);
        map.put("option", courses);
        return map;
    }

    @Override
    public Map save(Course course) {
        Map map = new HashMap();
        course.setId(UUID.randomUUID().toString().replace("-", "")).setType("专属").setCreateDate(new Date());
        int i = courseDao.insert(course);
        if (i == 1) {
            map.put("status", 200);
            map.put("option", course);
        } else {
            map.put("status", -200);
            map.put("message", "功课插入失败");
        }
        return map;
    }

    @Override
    public Map delete(Course course) {
        Map map = new HashMap();
        Course course1 = courseDao.selectOne(course);
        int i = courseDao.delete(course);
        if (i == 1) {
            map.put("status", 200);
            map.put("option", course1);
        } else {
            map.put("status", -200);
            map.put("message", "删除失败");
        }
        return map;
    }
}
