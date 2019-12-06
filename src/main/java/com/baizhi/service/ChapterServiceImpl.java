package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;
    @Override
    public Map findByCurrentPage(String id,Integer page, Integer rows) {
        //rows records total page
        Map map = new HashMap();
        List<Chapter> albums = chapterDao.findByCurrent(id, (page - 1) * rows, rows);
        int records = chapterDao.findCount(id);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", albums);
        map.put("records", records);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    @LogAnnotation(value = "上传章节")
    public Map save(Chapter chapter) {
        Map map = new HashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        chapter.setId(id);
        chapterDao.insert(chapter);
        Album album = albumDao.selectByPrimaryKey(chapter.getAlbumId());
        System.out.println("-------"+album.setCount(album.getCount() + 1));
        albumDao.updateByPrimaryKey(album.setCount(album.getCount() + 1));
        map.put("chapterId", id);
        map.put("status", 200);
        return map;
    }

    @Override
    public Map update(Chapter chapter) {
        Map map = new HashMap();
        chapter.setUrl(null);
        chapter.setAlbumId(null);
        chapterDao.updateByPrimaryKeySelective(chapter);
        map.put("chapterId", chapter.getId());
        map.put("status", 200);
        return map;
    }

    @Override
    public Map deleteList(List<String> list) {
        Map map = new HashMap();
        chapterDao.deleteByIdList(list);
        map.put("status", 200);
        return map;
    }

    @Override
    public Map findAllSearch(String id,String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        Map map = new HashMap();
        List<Chapter> chapters = chapterDao.findAllSearch(id,searchField, searchString, searchOper, (page - 1) * rows, rows);
        int records = chapterDao.findTotalCountsSearch(id,searchField, searchString, searchOper);
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", chapters);
        map.put("records", records);
        map.put("total", total);
        map.put("page", page);
        return map;
    }
}
