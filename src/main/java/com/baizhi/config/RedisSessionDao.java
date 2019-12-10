package com.baizhi.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

//交给spring工厂管理
@Component
public class RedisSessionDao extends AbstractSessionDAO {
    //设置redis过期时间
    private long expireTime = 1800;
    //注入redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        //获取sessionId
        Serializable sessionId = this.generateSessionId(session);
        //session绑定
        this.assignSessionId(session, sessionId);
        //存入session
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        return sessionId;
    }

    //读取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return sessionId == null ? null : (Session) redisTemplate.opsForValue().get(sessionId);
    }

    //更新session
    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            session.setTimeout(expireTime * 1000);
            redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        }
    }

    //删除sesssion
    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            redisTemplate.opsForValue().getOperations().delete(session.getId());
        }
    }

    //获取有效session
    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }

}
