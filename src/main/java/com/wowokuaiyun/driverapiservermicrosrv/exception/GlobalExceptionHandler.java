package com.wowokuaiyun.driverapiservermicrosrv.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常返回类
 *
 * @author DingJie on 2018/12/21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Object allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        exception.printStackTrace();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", "500");
        result.put("message", exception.getMessage());
        return result;
    }
}
