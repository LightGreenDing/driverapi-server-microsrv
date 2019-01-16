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
     * 发送短信
     *
     * @param loginAuthJson 请求数据
     * @return json字符串
     */
    String loginAuth(String loginAuthJson);

    /**
     * 发送短信
     *
     * @param driverJson 请求数据
     * @return json字符串
     */
    String getDriverInfo(String driverJson);
}
