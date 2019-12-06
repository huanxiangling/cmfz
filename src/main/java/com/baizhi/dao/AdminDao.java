package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace
public interface AdminDao extends Mapper<Admin> {
}
