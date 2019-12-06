package com.baizhi.task;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.ApplicationContextUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@Async
public class MySpringTask {

    @Scheduled(cron ="0 0 0 1 1/1 ?")
    public void task1() {
        //创建excel
        //寻找数据
        BannerDao bannerDao = (BannerDao) ApplicationContextUtils.getBean("bannerDao");
        List<Banner> banners = bannerDao.selectAll();
        String path = System.getProperty("user.dir");
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String realPath =path+"\\src\\main\\webapp"+"\\"+ split[4] + "\\" + split[5];
            banner.setUrl(realPath+ "\\" + split[6]);
        }
        //创建输出流
        //绝对路径
        String excelPath = path+"\\src\\main\\webapp"+"\\"+"excel"+"\\";
        String fileName = "定时的轮播图.xls";
        File file = new File(excelPath);
        if(!file.exists()){
            file.mkdirs();
        }
        File file1 = new File(excelPath, fileName);
        EasyExcel.write(file1, Banner.class).sheet("轮播图信息").doWrite(banners);
        System.out.println("success");
    }
}
