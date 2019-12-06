package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
@EnableScheduling
public class CmfzApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmfzApplication.class, args);
	}
	/**
	 * 解决request空指针
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}

}
