package com.wowokuaiyun.driverapiservermicrosrv.service;

/**
 * 司机端服务接口
 *
 * @author DingJie on 2018/1/2
 */
public interface DriverService {
    /**
     * 发送短信
     *
     * @param smsJson 请求数据
     * @return json字符串
     */
    String sendSms(String smsJson);

    /**
     * 登录验证
     *
     * @param loginAuthJson 请求数据
     * @return json字符串
     */
    String loginAuth(String loginAuthJson);

    /**
     * 通过司机ID获取司机信息
     *
     * @param driverJson 请求数据
     * @return json字符串
     */
    String getDriverInfo(String driverJson);
}
