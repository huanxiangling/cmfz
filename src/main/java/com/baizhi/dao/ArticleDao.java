package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article>,DeleteByIdListMapper<Article,String> {
    List<Article> findAllSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper, @Param("start")Integer start, @Param("rows")Integer rows);

    Integer findTotalCountsSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper);
}
