package com.baizhi.controller;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import javazoom.jl.player.Player;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;

    @RequestMapping("findByCurrentPage")
    public Map findByCurrentPage(String searchField,String searchString,String searchOper,String albumId,Boolean _search,Integer page,Integer rows) {
        if (_search) {
            return chapterService.findAllSearch(albumId, searchField, searchString, searchOper, page, rows);
        } else {
            return chapterService.findByCurrentPage(albumId,page, rows);
        }

    }
    @RequestMapping("change")
    public Map change(String oper,String albumId, Chapter chapter, String[] id,HttpServletRequest request) {
        if (oper.equals("add")) {
            System.out.println("albumId:"+albumId);
            return chapterService.save(chapter.setAlbumId(albumId));
        } else if (oper.equals("edit")) {
            System.out.println(oper);
            return chapterService.update(chapter);
        } else {
            List<String> list = new ArrayList<>();
            for (String s : id) {
                list.add(s);
            }
            return chapterService.deleteList(list);
        }
    }

    @RequestMapping("player")
    public void player(String chapterId,HttpServletRequest request) throws Exception {
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        Chapter chapter1 = chapterDao.selectByPrimaryKey(chapter);
        String[] strings = chapter1.getUrl().split("/");
        String s = "/"+strings[4] +"/"+ strings[5];
        String fileName=strings[6];
        String realPath = request.getSession().getServletContext().getRealPath(s);
        File file = new File(realPath,fileName);
        Player player = new Player(new FileInputStream(file));
        player.play();
    }
    @RequestMapping("download")
    public void download(String openStyle, String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //attachment和inline
        openStyle = openStyle == null ? "attachment" : openStyle;
        String[] strings = url.split("/");
        String s = "/"+strings[4] +"/"+ strings[5];
        String fileName=strings[6];
        String realPath = request.getSession().getServletContext().getRealPath(s);
        File file = new File(realPath,fileName);
        //获取输入流
        FileInputStream in = new FileInputStream(file);
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取输出流
        ServletOutputStream os = response.getOutputStream();
        //IO拷贝
        IOUtils.copy(in, os);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(os);
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String chapterId, HttpServletRequest request,String albumId) throws Exception {
        //相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/files");
        String fileNamePrefix = UUID.randomUUID().toString().replace("-", "")
                + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        String fileNameSuffix = FilenameUtils.getExtension(url.getOriginalFilename());
        String fileName = fileNamePrefix + "." + fileNameSuffix;
        //创建新的日期目录
        String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateDir = new File(realPath, dateDirString);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        File file = new File(dateDir, fileName);
        //生成上传文件
        url.transferTo(file);
        long length = file.length();
        String size=length/1024/1024+"MB";
        MP3File read = (MP3File) AudioFileIO.read(file);
        MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
        int trackLength = mp3AudioHeader.getTrackLength();
        String min=trackLength/60+"分";
        String sed=trackLength%60+"秒";
        //获取网络路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/files/" + dateDirString + "/" + fileName;
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setSize(size);
        chapter.setTime(min + sed);
        chapter.setUrl(uri);
        chapter.setAlbumId(albumId);
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

}
