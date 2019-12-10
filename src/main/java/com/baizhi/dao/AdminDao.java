package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace
public interface AdminDao extends Mapper<Admin> {
    Admin queryAdminInfo(String username);

    List<Admin> findAll(@Param("start") Integer start, @Param("rows") Integer rows);

    List<Admin> findAllSearch(
            @Param("searchField") String searchField, @Param("searchString") String searchString, @Param("searchOper") String searchOper, @Param("start") Integer start, @Param("rows") Integer rows);

    Integer findTotalCountsSearch(
            @Param("searchField") String searchField, @Param("searchString") String searchString, @Param("searchOper") String searchOper);
}
