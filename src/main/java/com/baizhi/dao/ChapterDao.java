package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter>,DeleteByIdListMapper<Chapter,String>{
    List<Chapter> findByCurrent(@Param("id")String id, @Param("start")Integer start, @Param("rows")Integer rows);

    Integer findCount(String id);

    List<Chapter> findAllSearch(
            @Param("id")String id,@Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper, @Param("start")Integer start, @Param("rows")Integer rows);

    Integer findTotalCountsSearch(
            @Param("id")String id,@Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper);
}
