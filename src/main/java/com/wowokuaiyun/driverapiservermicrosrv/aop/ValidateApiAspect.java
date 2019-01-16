package com.wowokuaiyun.driverapiservermicrosrv.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wowokuaiyun.driverapiservermicrosrv.base.ResultData;
import com.wowokuaiyun.driverapiservermicrosrv.entity.Driver;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 拦截请求中的appId进行判断
 * 设置以自定义annotation作为切入点
 *
 * @author DingJie on 2018/12/4
 */
@Component
@Aspect
public class ValidateApiAspect {
    private Logger log = Logger.getLogger(ValidateApiAspect.class);
    @Value("${spring.api.checkappid}")
    private String checkAppId;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private RedisTemplate redisTemplate;

    @Before("@annotation(com.wowokuaiyun.driverapiservermicrosrv.aop.ValidateApi)")
    public void checkPermission(JoinPoint joinPoint) throws Exception {
        String authorization = httpServletRequest.getHeader("Authorization");
        String[] split = authorization.split(" ");
        log.info("token:" + split[1]);
        String token = split[1];
        log.info("移动端请求token:" + token);
        Map map = (Map) redisTemplate.opsForValue().get("token2DriverMap");
        JSONObject object = (JSONObject) map.get(token);
        Driver driver = JSON.toJavaObject(object, Driver.class);
        if (driver == null) {
            log.info("验证不通过");
            throw new Exception("没有权限");
        }

        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);    //得到拦截的方法

        log.info("请求方法:" + method.getName());

        Object[] args = joinPoint.getArgs();
        String request = (String) args[0];
        log.info("请求参数::" + request);
        ResultData resultData = JSON.parseObject(request, ResultData.class);
        String appId = resultData.getApp_id();
        String[] appIdSplit = checkAppId.split(",");
        boolean contains = false;
        for (String s : appIdSplit) {
            if (appId.equals(s)) {
                contains = true;
            }
        }
        if (!contains) {
            log.info("验证不通过:非法应用请求");
            throw new Exception("非法应用请求");
        }
    }

    /**
     * 根据类和方法名得到方法
     */
    public Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
