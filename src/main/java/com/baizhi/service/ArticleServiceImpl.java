package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    public Map save(Article article) {
        Map map = new HashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        article.setId(id);
        articleDao.insert(article);
        map.put("articleId", id);
        map.put("status", 200);
        return map;
    }

    @Override
    public Map findByCurrentPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(new Article(), new RowBounds((page - 1) * rows, rows));
        int records = articleDao.selectCount(new Article());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", articles);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }

    @Override
    public Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Article> articles = articleDao.findAllSearch(searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = articleDao.findTotalCountsSearch(searchField, searchString, searchOper);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", articles);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }

    @Override
    public Map update(Article article) {
        Map map = new HashMap();
        articleDao.updateByPrimaryKeySelective(article);
        map.put("articleId", article.getId());
        map.put("status", 200);
        return map;
    }


    @Override
    public Map deleteList(List<String> list) {
        Map map = new HashMap();
        articleDao.deleteByIdList(list);
        map.put("status", 200);
        return map;
    }

    @Override
    public Article findOne(String id) {
        Article article = new Article().setId(id);
        return articleDao.selectOne(article);
    }
}
