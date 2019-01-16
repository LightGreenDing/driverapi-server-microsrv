package com.wowokuaiyun.driverapiservermicrosrv.request;

/*
 * 请求短信封装
 * @Author Ding.jie
 **/

public class MobileRequest {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 短信模板
     */
    private String templateid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }
}
