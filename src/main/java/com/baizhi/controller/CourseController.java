package com.baizhi.controller;

import com.baizhi.entity.Course;
import com.baizhi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("findCourse")
    public Map findCourse(Course course) {
        return courseService.findOne(course);
    }
    @RequestMapping("findAllCourse")
    public Map findAllCourse(String uid) {
        System.out.println(uid);
        return courseService.findAll(uid);
    }
    @RequestMapping("saveCourse")
    public Map saveCourse(Course course) {
        return courseService.save(course);
    }
    @RequestMapping("deleteCourse")
    public Map deleteCourse(Course course) {
        return courseService.delete(course);
    }
}
