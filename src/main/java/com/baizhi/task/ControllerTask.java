package com.baizhi.task;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Component
public class ControllerTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void run() {
        //定义一个定时器
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                //业务代码
                //创建excel
                //寻找数据
                BannerDao bannerDao = (BannerDao) ApplicationContextUtils.getBean("bannerDao");
                List<Banner> banners = bannerDao.selectAll();
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
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
            }
        };

        threadPoolTaskScheduler.schedule(runnable, new CronTrigger("* 0/1 * * * *"));
    }

    public void shutdown() {
        threadPoolTaskScheduler.shutdown();
    }
}
