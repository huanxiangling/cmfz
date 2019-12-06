package com.baizhi.service;

import com.baizhi.entity.Counter;

import java.util.Map;

public interface CounterService {
    Map save(Counter counter);

    Map delete(Counter counter);

    Map update(Counter counter);

    Map findByUserIdAndCourseId(Counter counter);
}
