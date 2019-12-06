package com.baizhi.service;

import com.baizhi.entity.Course;

import java.util.Map;

public interface CourseService {
    Map findOne(Course course);

    Map findAll(String uid);

    Map save(Course course);

    Map delete(Course course);

}
