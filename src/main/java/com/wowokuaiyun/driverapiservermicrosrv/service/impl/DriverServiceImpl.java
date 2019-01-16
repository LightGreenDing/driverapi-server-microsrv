package com.wowokuaiyun.driverapiservermicrosrv.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wowokuaiyun.driverapiservermicrosrv.aop.ValidateApi;
import com.wowokuaiyun.driverapiservermicrosrv.base.BaseService;
import com.wowokuaiyun.driverapiservermicrosrv.base.ResultData;
import com.wowokuaiyun.driverapiservermicrosrv.dao.DriverMapper;
import com.wowokuaiyun.driverapiservermicrosrv.entity.Driver;
import com.wowokuaiyun.driverapiservermicrosrv.entity.DriverExample;
import com.wowokuaiyun.driverapiservermicrosrv.request.LoginRequet;
import com.wowokuaiyun.driverapiservermicrosrv.request.MobileRequest;
import com.wowokuaiyun.driverapiservermicrosrv.request.SendSMSRequest;
import com.wowokuaiyun.driverapiservermicrosrv.service.DriverService;
import com.wowokuaiyun.driverapiservermicrosrv.utils.MD5Util;
import hprose.client.HproseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DriverServiceImpl extends BaseService implements DriverService {
    protected static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String sendSms(String smsJson) {
        ResultData resultData = JSON.parseObject(smsJson, ResultData.class);
        JSONObject jsonObject = JSON.parseObject(resultData.getData());
        String mobile = String.valueOf(jsonObject.get("mobile"));
        String code = (String) redisTemplate.opsForValue().get(mobile);
        if (code != null && !code.isEmpty()) {
            return success(code);
        }
        MobileRequest mobileRequest = new MobileRequest();
        mobileRequest.setMobile(mobile);
        //TODO 短信模板暂时写死
        mobileRequest.setTemplateid("9374175");
        SendSMSRequest sendSMSRequest = new SendSMSRequest();
        sendSMSRequest.setData(mobileRequest);
        String smsRequest = JSON.toJSONString(sendSMSRequest);
        HproseClient client = null;
        //调用短信微服务发送验证码
        try {
            //TODO 以后替换为接口
            client = HproseClient.create("http://192.168.1.8:8081/api/v1.0.0/sms/sendsmscode");
            Object result = client.invoke("sendsmscode", new Object[]{smsRequest});
            JSONObject object = JSON.parseObject(String.valueOf(result));
            Map<String, String> data = (Map<String, String>) object.get("data");
            String vcode = data.get("vcode");
            //将短信和验证码存入redis中,设置有效时间为5分钟
            redisTemplate.opsForValue().set(mobile, vcode, 5, TimeUnit.MINUTES);
            logger.info("短信微服务:{[mobile:" + mobile + "验证码:" + vcode + "]}");
            return success(vcode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用短信微服务失败");
            return error(501, "调用短信微服务失败");
        }

    }

    @Override
    public String loginAuth(String loginAuthJson) {
        //解析请求数据
        ResultData resultData = JSON.parseObject(loginAuthJson, ResultData.class);
        String data = resultData.getData();
        LoginRequet loginRequet = JSON.parseObject(data, LoginRequet.class);
        //从缓存中拿到短信验证码进行对比
        String vcode = (String) redisTemplate.opsForValue().get(loginRequet.getUsername());
        if (vcode == null || vcode.isEmpty()) {
            return error(502, "短信验证码错误");
        }
        if (!vcode.equals(loginRequet.getCode())) {
            return error(502, "短信验证码错误");
        }

        /**
         *  判断是否登录成功
         *  1.登录成功
         *   1.1.成功生成对应的token并更新
         *   1.2.失败就抛异常
         */
        Boolean aBoolean = redisTemplate.hasKey("driver2TokenMap");
        if (!aBoolean) {
            /**
             * 存放“用户名：token”键值对
             */
            Map<String, String> driver2Token = new HashMap<String, String>();
            redisTemplate.opsForValue().set("driver2TokenMap", driver2Token);
        }
        Map driver2TokenMap = (Map) redisTemplate.opsForValue().get("driver2TokenMap");
        Boolean userMap = redisTemplate.hasKey("token2DriverMap");
        if (!userMap) {
            /**
             * 存放“token:User”键值对
             */
            Map<String, Driver> token2User = new HashMap<>();
            redisTemplate.opsForValue().set("token2DriverMap", token2User);
        }
        Map token2DriverMap = (Map) redisTemplate.opsForValue().get("token2DriverMap");
        String token = (String) driver2TokenMap.get(loginRequet.getUsername());
        Driver driver = null;
        if (token == null) {
            driver = new Driver();
            driver.setUsername(loginRequet.getUsername());
            driver.setCode(Integer.valueOf(loginRequet.getCode()));
            driver.setMobile(loginRequet.getUsername());
            driverMapper.insertSelective(driver);
            logger.info("新用户登录");
        } else {
            JSONObject object = (JSONObject) token2DriverMap.get(token);
            driver = JSON.toJavaObject(object, Driver.class);
            token2DriverMap.remove(token);
            logger.info("更新用户登录token");
        }
        token = MD5Util.MD5(loginRequet.getUsername() + loginRequet.getCode() + System.currentTimeMillis());
        token2DriverMap.put(token, driver);
        driver2TokenMap.put(loginRequet.getUsername(), token);
        logger.info("目前有【" + driver2TokenMap.size() + "】个用户在线");
        redisTemplate.opsForValue().set("token2DriverMap", token2DriverMap);
        redisTemplate.opsForValue().set("driver2TokenMap", driver2TokenMap);

        //登陆成功以后删除掉验证码 下次登录重新生成验证码
        redisTemplate.delete(loginRequet.getUsername());
        return success(token);
    }

    @ValidateApi
    @Override
    public String getDriverInfo(String driverJson) {
        ResultData resultData = JSON.parseObject(driverJson, ResultData.class);
        JSONObject jsonObject = JSON.parseObject(resultData.getData());
        String driverid = (String) jsonObject.get("driverid");
        DriverExample driverExample = new DriverExample();
        driverExample.createCriteria().andIdEqualTo(Long.valueOf(driverid));
        List<Driver> drivers = driverMapper.selectByExample(driverExample);
        if (drivers.get(0) != null) {
            return success(drivers.get(0));
        } else {
            return error(505, "无此用户");
        }
    }
}
