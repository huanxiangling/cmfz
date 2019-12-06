package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace
public interface BannerDao extends Mapper<Banner>,DeleteByIdListMapper<Banner,String>,InsertListMapper<Banner>{
    List<Banner> findAllSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper, @Param("start")Integer start, @Param("rows")Integer rows);

    Integer findTotalCountsSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper);
}
