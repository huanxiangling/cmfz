package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {

    Map save(Banner banner);

    Map findByCurrentPage(Integer page, Integer rows);

    Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

    Integer findTotalCountsSearch(String searchField, String searchString, String searchOper);


    Map update(Banner banner);

    Map delete(Banner banner);

    Map deleteList(List<String> list);

    void insertList(List<Banner> list);
}



