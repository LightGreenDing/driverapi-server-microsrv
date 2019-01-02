package com.wowokuaiyun.driverapiservermicrosrv.base;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * 父类Servic
 * 定义返回数据封装
 *
 * @author Ding.Jie
 */
public class BaseService {

    public static String success(Object object) {
        ResponeResult responeResult = new ResponeResult();
        responeResult.setCode(0);
        responeResult.setMsg("成功");
        responeResult.setData(object);
        return JSON.toJSONString(responeResult);
    }

    public static String error() {
        ResponeResult responeResult = new ResponeResult();
        responeResult.setCode(-1);
        responeResult.setMsg("失败");
        return JSON.toJSONString(responeResult);
    }

    public static String success() {
        return success(null);
    }

    public static String error(Integer code, String msg) {
        ResponeResult responeResult = new ResponeResult();
        responeResult.setCode(code);
        responeResult.setMsg(msg);
        responeResult.setData(new ArrayList<>());
        return JSON.toJSONString(responeResult);
    }
}
