package com.baizhi.controller;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumDao albumDao;

    @RequestMapping("detailsAlbum")
    public Map realArticle(String uid, String id) {
        Map map = new HashMap();
        Album album = new Album();
        Album one = albumDao.selectOne(album.setId(id));
        map.put("status", 200);
        map.put("article", one);
        return map;
    }

    @RequestMapping("findByCurrentPage")
    public Map findByCurrentPage(String searchField, String searchString, String searchOper, Boolean _search, Integer page, Integer rows) {
        if (_search) {
            return albumService.findAllSearch(searchField, searchString, searchOper, page, rows);
        } else {
            return albumService.findByCurrentPage(page, rows);
        }

    }
    @RequestMapping("change")
    public Map change(String oper, Album album, String[] id) {
        if (oper.equals("add")) {
            return albumService.save(album);
        } else if (oper.equals("edit")) {
            return albumService.update(album);
        } else {
            List<String> list = new ArrayList<>();
            System.out.println(oper);
            for (String s : id) {
                list.add(s);
            }
            return albumService.deleteList(list);
        }
    }
    @RequestMapping("upload")
    public void upload(MultipartFile cover, String albumId, HttpServletRequest request) throws IOException {
        //相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/files");
        String fileNamePrefix = UUID.randomUUID().toString().replace("-", "")
                + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        String fileNameSuffix = FilenameUtils.getExtension(cover.getOriginalFilename());
        String fileName = fileNamePrefix + "." + fileNameSuffix;
        //创建新的日期目录
        String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateDir = new File(realPath, dateDirString);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        //生成上传文件
        cover.transferTo(new File(dateDir, fileName));
        //获取网络路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/files/" + dateDirString + "/" + fileName;
        System.out.println("路径为"+uri);
        Album album = new Album();
        album.setId(albumId);
        album.setCover(uri);
        albumDao.updateByPrimaryKeySelective(album);
    }
}
