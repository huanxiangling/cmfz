package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>,DeleteByIdListMapper<User,String>,SelectByIdListMapper<User,String> {
    List<User> findAllSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper, @Param("start")Integer start, @Param("rows")Integer rows);

    Integer findTotalCountsSearch(
            @Param("searchField")String searchField, @Param("searchString")String searchString, @Param("searchOper")String searchOper);

    Integer findCountManDay(Integer num);

    Integer findCountWomenDay(Integer num);

}
