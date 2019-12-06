package com.baizhi.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

public class FileUtil {
    public static String getHttpUrl(MultipartFile file, HttpServletRequest request, HttpSession session, String dir) {
        // 获取路径
        String realPath = session.getServletContext().getRealPath(dir);
        // 判断路径文件夹是否存在
        File f = new File(realPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        // 防止重名操作
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replace("-","")+new Date().getTime() + "_" + originalFilename;
        try {
            file.transferTo(new File(realPath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 相对路径 : ../XX/XX/XX.jpg
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        // 网络路径拼接
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + dir + fileName;
        return uri;
    }

    public static void download(String openStyle,String dir, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //attachment和inline
        openStyle = openStyle == null ? "attachment" : openStyle;
        String filePath="as";
        //动态获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath(dir);
        //读取下载的指定文件
        File file = new File(realPath, fileName);
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
