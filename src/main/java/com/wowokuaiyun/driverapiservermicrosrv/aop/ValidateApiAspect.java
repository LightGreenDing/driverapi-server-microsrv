package com.wowokuaiyun.driverapiservermicrosrv.aop;

import com.alibaba.fastjson.JSON;
import com.wowokuaiyun.driverapiservermicrosrv.base.ResultData;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

    @Before("@annotation(com.wowokuaiyun.driverapiservermicrosrv.aop.ValidateApi)")
    public void checkPermission(JoinPoint joinPoint) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);    //得到拦截的方法

        log.info("请求方法:" + method.getName());

        Object[] args = joinPoint.getArgs();
        String request = (String) args[0];
        log.info("请求参数::" + request);
        ResultData resultData = JSON.parseObject(request, ResultData.class);
        String appId = resultData.getApp_id();
        String[] split = checkAppId.split(",");
        boolean contains = false;
        for (String s : split) {
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
