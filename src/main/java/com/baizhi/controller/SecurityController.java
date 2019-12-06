package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("image")
public class SecurityController {

    @RequestMapping("img")
    public void getImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取code
        String code = SecurityCode.getSecurityCode();
        request.getSession().setAttribute("code", code);
        //获取图片
        BufferedImage image = SecurityImage.createImage(code);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }
}
