package com.baizhi;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基础数据类
 * @ExcelProperty 指定列名
 * @ExcelIgnore 忽略字段
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DemoData {
    @ExcelProperty(value = {"标题","字符串标题"},converter = CustomStringStringConverter.class)
    private String string;
    @ExcelProperty({"标题","日期标题"})
    @DateTimeFormat("yyyy年MM月dd日")
    private Date date;
    @ExcelProperty({"标题","数字标题"})
    @NumberFormat("#.##%")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
