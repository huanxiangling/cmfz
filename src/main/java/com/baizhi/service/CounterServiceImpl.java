package com.baizhi.service;

import com.baizhi.dao.CounterDao;
import com.baizhi.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;

    @Override
    public Map save(Counter counter) {
        Map map = new HashMap();
        counter.setId(UUID.randomUUID().toString().replace("-", "")).setCreateDate(new Date());
        int i = counterDao.insert(counter);
        if (i == 1) {
            map.put("status", 200);
            map.put("counters", counter);
        } else {
            map.put("status", -200);
            map.put("message", "计数器创建失败");
        }
        return map;
    }

    @Override
    public Map delete(Counter counter) {
        Map map = new HashMap();
        Counter deleteCounter = counterDao.selectOne(counter);
        int i = counterDao.delete(counter);
        if (i == 1) {
            map.put("status", 200);
            map.put("counters", deleteCounter);
        } else {
            map.put("status", -200);
            map.put("message", "计数器删除失败");
        }
        return map;
    }

    @Override
    public Map update(Counter counter) {
        Map map = new HashMap();
        int i = counterDao.updateByPrimaryKeySelective(counter);
        Counter counter1 = counterDao.selectOne(counter);
        if (i == 1) {
            map.put("status", 200);
            map.put("counters", counter1);
        } else {
            map.put("status", -200);
            map.put("message", "计数器更新失败");
        }
        return map;
    }

    @Override
    public Map findByUserIdAndCourseId(Counter counter) {
        Map map = new HashMap();
        List<Counter> counters = counterDao.select(counter);
        map.put("status", 200);
        map.put("counters", counters);
        return map;
    }
}
