package com.baizhi.config;

import com.baizhi.realm.MyRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

//1.声明shiro过滤器
@Configuration
public class ShiroFilter {
    //2.声明一个ShiroFilterFactoryBean对象交给srping工厂管理
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        //通过ShiroFilterFactoryBean 配置整个shiro过滤器
        //3.创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //4.配置 过滤器链 注意: 1.使用LinkedHashMap 2.不认证anon过滤器声明在前面
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        //5.静态资源放行
        linkedHashMap.put("/bannerPOI/**", "anon");
        linkedHashMap.put("/boot/**", "anon");
        linkedHashMap.put("/echarts/**", "anon");
        linkedHashMap.put("/excel/**", "anon");
        linkedHashMap.put("/files/**", "anon");
        linkedHashMap.put("/img/**", "anon");
        linkedHashMap.put("/jqgrid/**", "anon");
        linkedHashMap.put("/js/**", "anon");
        linkedHashMap.put("/kindeditor/**", "anon");
        linkedHashMap.put("/upload/**", "anon");
        //6.登陆放行
        linkedHashMap.put("/admin/login", "anon");
        linkedHashMap.put("/image/img", "anon");
        //7.其他拦截
        linkedHashMap.put("/**", "authc");
        //8.将过滤器链交给shiroFilterFactoryBean管理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        //9.设置登录URL
        shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        //10.将DefaultWebSecurityManager对象交由shiroFilterFactoryBean管理
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    //创建SecurityManager对象 交给spring工厂管理
    @Bean
    public SecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        //使用shiro缓存
        EhCacheManager ehCacheManager = new EhCacheManager();
        securityManager.setCacheManager(ehCacheManager);
        securityManager.setSessionManager(defaultWebSessionManager);
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(RedisSessionDao redisSessionDao) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800 * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        /**
         * 修改Cookie中的SessionId的key，默认为JSESSIONID，自定义名称
         */
        sessionManager.setSessionIdCookie(new SimpleCookie("JSESSIONID"));
        return sessionManager;
    }
}


