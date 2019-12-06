package com.baizhi.listener;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws IOException {
        // 将value 截取 转换为绝对路径
        String[] split = value.split("\\\\");
        String path = System.getProperty("user.dir");
        String realPath = path +"\\src\\main\\webapp"+ "\\" + split[8]+ "\\" + split[9];
        String s = realPath + "\\" + split[10];
        // new File(绝对路径)
        return new CellData(FileUtils.readFileToByteArray(new File(s)));
    }
}
