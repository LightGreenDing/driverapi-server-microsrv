package com.wowokuaiyun.driverapiservermicrosrv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author DingJie on 2018/1/2
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "司机端APP接口聚合微服务";
    }
}
