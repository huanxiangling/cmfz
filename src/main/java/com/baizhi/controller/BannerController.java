package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.listener.BannerListener;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @RequestMapping("showAllBanners")
    public Map showAllBanners(String searchField,String searchString,String searchOper,Boolean _search,Integer page,Integer rows) {
        if (_search) {
            return bannerService.findAllSearch(searchField, searchString, searchOper, page, rows);
        } else {
            return bannerService.findByCurrentPage(page, rows);
        }
    }

    @RequestMapping("change")
    public Map change(String oper,Banner banner,String[] id) {
        if (oper.equals("add")) {
            return bannerService.save(banner);
        } else if (oper.equals("edit")) {
            return bannerService.update(banner);
        } else {
            List<String> list = new ArrayList<>();
            for (String s : id) {
                list.add(s);
            }
            return bannerService.deleteList(list);
        }
    }

    @RequestMapping("export")
    public void export(String openStyle,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建excel
        //寻找数据
        List<Banner> banners = bannerDao.selectAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String realPath = request.getSession().getServletContext().getRealPath("\\" + split[4] + "\\" + split[5] + "\\");
            banner.setUrl(realPath+ "\\" + split[6]);
        }
        //创建输出流
        //绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/excel");
        String fileName = "轮播图.xls";
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        File file1 = new File(realPath, fileName);
        EasyExcel.write(file1, Banner.class).sheet("轮播图信息").doWrite(banners);
        //导出excel
        //attachment和inline
        openStyle = openStyle == null ? "attachment" : openStyle;
        //获取输入流
        FileInputStream in = new FileInputStream(file1);
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取输出流
        ServletOutputStream os = response.getOutputStream();
        //IO拷贝
        IOUtils.copy(in, os);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(os);
    }

    @RequestMapping("inputPOI")
    public void inputPOI(MultipartFile ff,String bannerId, HttpServletRequest request) throws IOException, ParseException {
        //相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/bannerPOI");
        String fileNamePrefix = UUID.randomUUID().toString().replace("-", "")
                + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        String fileNameSuffix = FilenameUtils.getExtension(ff.getOriginalFilename());
        String fileName = fileNamePrefix + "." + fileNameSuffix;
        //创建新的日期目录
        String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateDir = new File(realPath, dateDirString);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        //生成上传文件
        File file = new File(dateDir, fileName);
        ff.transferTo(file);
        //获取要导入的文件
        EasyExcel.read(file,Banner.class,new BannerListener()).sheet().doRead();
    }
    @RequestMapping("exportModel")
    public void exportModel(String openStyle,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建excel
        //寻找数据
        Banner banner = new Banner();
        List list = new ArrayList();
        list.add(banner);
        //创建输出流
        //绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/excel");
        String fileName = "轮播图模板.xls";
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        File file1 = new File(realPath, fileName);
        EasyExcel.write(file1, Banner.class).sheet("轮播图模板").doWrite(list);
        //导出excel
        //attachment和inline
        openStyle = openStyle == null ? "attachment" : openStyle;
        //获取输入流
        FileInputStream in = new FileInputStream(file1);
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取输出流
        ServletOutputStream os = response.getOutputStream();
        //IO拷贝
        IOUtils.copy(in, os);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(os);
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url,String bannerId, HttpServletRequest request) throws IOException {
        System.out.println(url.getOriginalFilename());
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
        //生成上传文件
        url.transferTo(new File(dateDir, fileName));
        //获取网络路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/files/" + dateDirString + "/" + fileName;
        System.out.println("路径为"+uri);
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(uri);
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @RequestMapping("download")
    public void download(String openStyle, String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //attachment和inline
        openStyle = openStyle == null ? "attachment" : openStyle;
        String filePath="as";
        //动态获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/files");
        //读取下载的指定文件
        String path = realPath + "\\" + filePath;
        String fileName = filePath.split("\\\\")[1];
        //读取下载的指定文件
        File file = new File(path, fileName);
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
}

