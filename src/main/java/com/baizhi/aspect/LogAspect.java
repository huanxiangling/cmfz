package com.baizhi.aspect;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public  Object addLogg(ProceedingJoinPoint proceedingJoinPoint) {
        // 谁 时间 事件 成功
        // 谁
        String admin = (String) request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        String status="";
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status= "success";
            return proceed;
        } catch (Throwable throwable) {
            status = "error";
            throwable.printStackTrace();
            return null;
        }finally {
            Log log = new Log(UUID.randomUUID().toString().replace("-", ""), admin, value, date, status);
            File file = new File("D:/", "cmfz.txt");  //创建文件对象
            try {
                if (!file.exists()) {				//如果文件不存在则新建文件
                    file.createNewFile();
                }
                FileOutputStream output = new FileOutputStream(file,true);
                OutputStreamWriter osw = new OutputStreamWriter(output, "UTF-8");//指定以UTF-8格式写入文件
                osw.write(log.toString());				//将数组的信息写入文件中
                osw.write("\r\n");
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
