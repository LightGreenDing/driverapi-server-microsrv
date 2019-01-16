package com.wowokuaiyun.driverapiservermicrosrv.request;

import java.io.Serializable;

/*
 *用户登录请求封装工具
 * @Author DingJie
 **/
public class LoginRequet implements Serializable {
    /**
     * 账号（暂时用手机号）
     */
    private String username;
    /**
     * 短信验证码
     */
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
