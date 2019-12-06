package com.baizhi.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.ApplicationContextUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class BannerListener extends AnalysisEventListener<Banner> {
    private  List list = new ArrayList();

    // 每行数据读取完成后会调用invoke方法
    @Override
    public void invoke(Banner banner, AnalysisContext context) {
        banner.setId(UUID.randomUUID().toString().replace("-", ""));
        list.add(banner);
    }
    // 全部读取后执行 doAfterAllAnalysed 方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //标准用法
        BannerDao bannerDao= (BannerDao) ApplicationContextUtils.getBean("bannerDao");
        bannerDao.insertList(list);

    }
}



