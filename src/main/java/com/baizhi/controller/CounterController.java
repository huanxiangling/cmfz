package com.baizhi.controller;

import com.baizhi.entity.Counter;
import com.baizhi.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    private CounterService counterService;

    @RequestMapping("save")
    public Map save(Counter counter) {
        return counterService.save(counter);
    }

    @RequestMapping("delete")
    public Map delete(Counter counter) {
        return counterService.delete(counter);
    }

    @RequestMapping("update")
    public Map update(Counter counter) {
        return counterService.update(counter);
    }

    @RequestMapping("findByUserIdAndCourseId")
    public Map findByUserIdAndCourseId(Counter counter) {

        return counterService.findByUserIdAndCourseId(counter);
    }
}