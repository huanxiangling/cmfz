package com.baizhi.aspect;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.util.ApplicationContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class MyCache {
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint pjp) {
        MethodSignature signature1 = (MethodSignature) pjp.getSignature();
        AddCache annotation = signature1.getMethod().getAnnotation(AddCache.class);
        String value = annotation.value();
        System.out.println("操作:" + value);
        String key = pjp.getTarget().getClass().getName();
        String minKey = "";
        String signature = pjp.getSignature().getName();
        minKey += signature;
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            minKey += arg;
        }
        try {
            //获取redisTemple
            RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
            Boolean hasKey = redisTemplate.opsForHash().hasKey(key, minKey);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            if (hasKey) {
                return redisTemplate.opsForHash().get(key, minKey);
            } else {
                Object proceed = pjp.proceed();
                redisTemplate.opsForHash().put(key, minKey, proceed);
                return proceed;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Around("@annotation(com.baizhi.annotation.DelCache)")
    public Object delCache(ProceedingJoinPoint pjp) {
        MethodSignature signature1 = (MethodSignature) pjp.getSignature();
        DelCache annotation = signature1.getMethod().getAnnotation(DelCache.class);
        String value = annotation.value();
        System.out.println("语言:" + value);
        String key = pjp.getTarget().getClass().getName();
        try {
            Object proceed = pjp.proceed();
            //获取stringRedisTemple
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) ApplicationContextUtils.getBean(StringRedisTemplate.class);
            stringRedisTemplate.delete(key);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
