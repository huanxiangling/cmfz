package com.baizhi;


import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
	@Autowired
	private AdminDao adminDao;

	@Test
	public void contextLoads() {

		List<Admin> admins = adminDao.selectAll();
		admins.forEach(admin-> System.out.println(admin));
	}

}
