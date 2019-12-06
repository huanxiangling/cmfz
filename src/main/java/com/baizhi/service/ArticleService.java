package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map save(Article article);

    Map findByCurrentPage(Integer page, Integer rows);

    Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

    Map update(Article article);

    Map deleteList(List<String> list);

    Article findOne(String id);
}
