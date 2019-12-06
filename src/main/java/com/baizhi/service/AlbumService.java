package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map findByCurrentPage(Integer page, Integer rows);

    Map save(Album album);

    Map update(Album album);

    Map deleteList(List<String> list);

    Map findAllSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

}
