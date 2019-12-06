package com.baizhi.dao;

import com.baizhi.entity.WorldMap;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorldMapDao extends Mapper<WorldMap> {
    List<WorldMap> findAll();
}
