package com.baizhi.controller;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("detailsArticle")
    public Map realArticle(String uid, String id) {
        Map map = new HashMap();
        Article one = articleService.findOne(id);
        map.put("status", 200);
        map.put("article", one);
        return map;
    }

    @RequestMapping("showAllArticles")
    public Map showAllBanners(String searchField,String searchString,String searchOper,Boolean _search,Integer page,Integer rows) {
        if (_search) {
            return articleService.findAllSearch(searchField, searchString, searchOper, page, rows);
        } else {
            return articleService.findByCurrentPage(page, rows);
        }
    }

    @RequestMapping("findOne")
    public Article findOne(String id) {
        return articleService.findOne(id);
    }

    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request) {
        System.out.println(imgFile.getOriginalFilename());
        HashMap hashMap = new HashMap();
        String dir = "/upload/articleImg/";
        //相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath(dir);
        String fileName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+"-" + imgFile.getOriginalFilename();
        File file = new File(realPath);
        System.out.println(!file.exists());
        //不存在创建
        if(!file.exists()){
            System.out.println("创建文件");
            file.mkdirs();
        }
        //生成上传文件
        try {
            imgFile.transferTo(new File(realPath, fileName));
            //获取网络路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + dir + fileName;
            hashMap.put("error",0);
            hashMap.put("url",uri);
        } catch (IOException e) {
            hashMap.put("error",0);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpSession session, HttpServletRequest request) {
        //获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        //准备返回的json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        //获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        //遍历文件夹中的文件
        for (File file1 : files) {
            HashMap hashMap1 = new HashMap();
            hashMap1.put("id_dir", false);
            hashMap1.put("has_file", false);
            hashMap1.put("filesize", file1.length());
            hashMap1.put("is_photo", true);
            hashMap1.put("filetype", FilenameUtils.getExtension(file1.getName()));
            hashMap1.put("filename", file1.getName());
            //获取上传时间
            String s = file1.getName().split("-")[0];
            System.out.println("s----"+s);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
            String format1 = format.format(new Date(Long.valueOf(s)));
            System.out.println(format1);
            hashMap1.put("datetime", format1);
            arrayList.add(hashMap1);
        }
        hashMap.put("file_list", arrayList);
        hashMap.put("total_count", files.length);
        //返回路径
        hashMap.put("current_url", request.getContextPath()+"/upload/articleImg/");
        return hashMap;
    }



    @RequestMapping("change")
    public Map change(String oper, Article article, String[] id) {
        List<String> list = new ArrayList<>();
        if ("del".equals(oper)) {
            for (String s : id) {
                list.add(s);
            }

        }
        return articleService.deleteList(list);
    }
    @RequestMapping("insertArticle")
    public void insertArticle(MultipartFile articleImg, Article article,HttpServletRequest request) throws IOException {
        if(articleImg.getSize()>0){
            System.out.println("-----------------------");
            //相对路径获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/files");
            String fileNamePrefix = UUID.randomUUID().toString().replace("-", "")
                    + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
            String fileNameSuffix = FilenameUtils.getExtension(articleImg.getOriginalFilename());
            String fileName = fileNamePrefix + "." + fileNameSuffix;
            //创建新的日期目录
            String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dateDir = new File(realPath, dateDirString);
            if(!dateDir.exists()){
                dateDir.mkdirs();
            }
            //生成上传文件
            articleImg.transferTo(new File(dateDir, fileName));
            //获取网络路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/files/" + dateDirString + "/" + fileName;
            article.setImg(uri);
        }
        System.out.println(article.getId());
        System.out.println(article.getId()== null||"".equals(article.getId()));
        if (article.getId()== null||"".equals(article.getId())) {
            System.out.println("null"+article);
            article.setCreateDate(new Date());
            articleService.save(article);
        } else {
            article.setPublishDate(new Date());
            articleService.update(article);
        }
    }

}

