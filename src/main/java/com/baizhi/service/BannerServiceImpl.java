package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @LogAnnotation(value = "插入轮播图")
    public Map save(Banner banner) {
        Map map = new HashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        banner.setId(id);
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);
        map.put("bannerId", id);
        map.put("status", 200);
        return map;
    }

    @Override
    @AddCache(value = "添加缓存")
    public Map findByCurrentPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        int records = bannerDao.selectCount(new Banner());
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", banners);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }


    @Override
    @AddCache(value = "添加search缓存")
    public Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Banner> banners = bannerDao.findAllSearch(searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = bannerDao.findTotalCountsSearch(searchField, searchString, searchOper);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", banners);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }

    @Override
    public Integer findTotalCountsSearch(String searchField, String searchString, String searchOper) {

        return null;
    }

    @Override
    public Map update(Banner banner) {
        Map map = new HashMap();
        banner.setUrl(null);
        bannerDao.updateByPrimaryKeySelective(banner);
        map.put("bannerId", banner.getId());
        map.put("status", 200);
        return map;
    }

    @Override
    public Map delete(Banner banner) {
        Map map = new HashMap();
        bannerDao.deleteByPrimaryKey(banner);
        map.put("bannerId", banner.getId());
        map.put("status", 200);
        return map;
    }

    @Override
    @DelCache(value = "删除缓存")
    public Map deleteList(List<String> list) {
        Map map = new HashMap();
        bannerDao.deleteByIdList(list);
        map.put("status", 200);
        return map;
    }

    @Override
    public void insertList(List<Banner> list) {
        bannerDao.insertList(list);
    }
}
