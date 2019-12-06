package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map findByCurrentPage(String id,Integer page, Integer rows);

    Map save(Chapter chapter);

    Map update(Chapter chapter);

    Map deleteList(List<String> list);

    Map findAllSearch(String id,String searchField, String searchString, String searchOper, Integer page, Integer rows);

}
