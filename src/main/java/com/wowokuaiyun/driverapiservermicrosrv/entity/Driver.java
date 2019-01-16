package com.wowokuaiyun.driverapiservermicrosrv.entity;

import com.wowokuaiyun.driverapiservermicrosrv.base.BaseEntity;

public class Driver extends BaseEntity {
    /**
     * 编号
     */
    private Long id;

    /**
     * 账户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 短信验证码
     */
    private Integer code;

    /**
     * 编号
     * @return id 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 编号
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 账户名
     * @return username 账户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 账户名
     * @param username 账户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 身份证号码
     * @return idcard 身份证号码
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 身份证号码
     * @param idcard 身份证号码
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 短信验证码
     * @return code 短信验证码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 短信验证码
     * @param code 短信验证码
     */
    public void setCode(Integer code) {
        this.code = code;
    }
}