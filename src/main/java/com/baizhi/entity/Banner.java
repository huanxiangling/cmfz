package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baizhi.listener.ImageConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ContentRowHeight(50)
@HeadRowHeight(30)
@ColumnWidth(25)
//轮播图
public class Banner implements Serializable {
    @Id
    @ExcelIgnore
    private String id;
    @ExcelProperty({"轮播图信息","标题"})
    private String title;
    @ExcelProperty(converter = ImageConverter.class,value = {"轮播图信息","图片"})
    private String url;
    @ExcelProperty({"轮播图信息","网络路径"})
    private String href;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ExcelProperty({"轮播图信息","上传时间"})
    private Date createDate;
    @Column(name = "`desc`")
    @ExcelProperty({"轮播图信息","描述"})
    private String desc;
    @ExcelProperty({"轮播图信息","状态(1:展示,2:冻结)"})
    private String status;

}
