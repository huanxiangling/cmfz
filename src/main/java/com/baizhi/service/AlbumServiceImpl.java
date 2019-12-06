package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
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
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Override
    public Map findByCurrentPage(Integer page, Integer rows) {
        //rows records total page
        Map map = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
        int records = albumDao.selectCount(new Album());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", albums);
        map.put("records", records);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    @LogAnnotation(value = "插入专辑")
    public Map save(Album album) {
        Map map = new HashMap();
        String s = UUID.randomUUID().toString().replace("-", "");
        album.setId(s).setCount(0);
        albumDao.insert(album);
        map.put("albumId", s);
        map.put("status", 200);
        return map;
    }

    @Override
    public Map update(Album album) {
        Map map = new HashMap();
        album.setCover(null);
        albumDao.updateByPrimaryKeySelective(album);
        map.put("albumId", album.getId());
        map.put("status", 200);
        return map;
    }

    @Override
    public Map deleteList(List<String> list) {
        Map map = new HashMap();
        albumDao.deleteByIdList(list);
        map.put("status", 200);
        return map;
    }

    @Override
    public Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Album> albums = albumDao.findAllSearch(searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = albumDao.findTotalCountsSearch(searchField, searchString, searchOper);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", albums);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        return hashMap;
    }
}
