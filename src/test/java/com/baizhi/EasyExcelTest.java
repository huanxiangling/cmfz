package com.baizhi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EasyExcelTest {
    String fileName="D:\\"+"1575268681853easyExcel.xls";

    @Test
    public void math() {
        int v = (int) (Math.random() * (1 - 1) + 1);
        System.out.println("v:"+v);
    }

    @Test
    public void test01() {
        DemoData demoData = new DemoData();
        List list = new ArrayList();
        list.add(demoData);
        Set<String> set = new HashSet();
        set.add("string");
        set.add("ignore");
        //路径
        String fileName = "D:\\"+new Date().getTime()+"easyExcel.xls";
        EasyExcel.write(fileName,DemoData.class).excludeColumnFiledNames(set).sheet("模板").doWrite(getDemoData());
    }

    public List<DemoData> getDemoData() {
        List list = new ArrayList();
        list.add(new DemoData("1", new Date(), 16.69, null));
        list.add(new DemoData("2", new Date(), 17.77, null));
        return list;
    }
    @Test
    public void test02() {
        EasyExcel.read(fileName,DemoData.class,new DemoDataListener()).sheet().doRead();
    }
    @Test
    public void test03() {
        EasyExcel.write(fileName,DemoData.class).sheet("模板").doWrite(getDemoData());
    }

    @Test
    public void test04() throws IOException {
        ImageData imageData = new ImageData();
        imageData.setFile(new File("E:\\壁纸\\hh.jpg"));
        imageData.setByteArray(FileUtils.readFileToByteArray(new File("E:\\壁纸\\hh.jpg")));
        imageData.setInputStream(new FileInputStream(new File("E:\\壁纸\\hh.jpg")));
        imageData.setString("E:\\壁纸\\hh.jpg");
        imageData.setUrl(new URL(
                "http://img3.cache.netease.com/game/2013/9/8/20130908220315770e6.jpg"));
        List<ImageData> imageData1 = Arrays.asList(imageData);
        EasyExcel.write(fileName,ImageData.class).sheet().doWrite(imageData1);
    }
}
