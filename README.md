# 微信小程序聚合服务
 项目启动类：DriverapiServerMicrosrvApplication
---
### 项目介绍：
本项目使用使用java语言开发的基础微服务：司机端APP聚合服务项目

#### 环境依赖：
SpringBoot Hprose Redis（具体请看POM文件）
#### 目录结构描述
com.wowokuaiyun.driverapiservermicrosrv

- java //代码库
- - aop      //切面
- - base      //父类
- - config   //配置
- - controller   //测试控制器
- - dao   //基础服务dao
- - entity //实体数据
- - exception//异常处理类
- - request  //请求参数封装
- - service  //服务接口
- - - impl //服务接口实现类
- - servlet //Hprose servlet
- - utils //工具类
- resources //配置文件
- - mapper //mapper xml文件
#### 主要方法及功能：

方法名| 功能 |  请求参数格式(data中的数据) |
| ------ | ------ | ------   
sendSms | 调用发送验证码接口 | {"mobile":17781695050}
loginAuth | 登录验证 |{"username":"17777777777","code":"491811"}
getDriverInfo | 通过司机ID获取司机信息 | {"id":"1081467587570831360"}
传值以及返回值都是json字符串格式，请求json格式示例：

```
{
	"data": {
		"mobile": "17781695050"
	},
	"app_id": "ANDROID_SHIPPER"
}
```


参数 | 功能
---|---
page | 第几页(从0开始)
size | 每页条数
id | 编号(例如:评论人ID,被评论人ID,订单号,评论ID)

返回json格式示例：

```
{
    "code": "", 
    "msg": "", 
    "data": ""
}
```

参数 | 功能
---|---
code | 成功失败的编码
msg | 返回消息
data | 返回数据

### 部署方式：
- 修改application.properties配置文件，指定正式环境
- 项目打包成jar包(mvn clean package -P dev)
- 然后运用命令：java -jar 打包的jar 例如（java -jar demo.jar）即可完成部署 。