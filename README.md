# Online_Class

## 总体介绍

### 使用框架

mysql 8.0 
springboot 2.1.4
mabatis
vue 2

### 使用的jar包

JWT 0.7.0        ：用于JWT 生成token并验证 

Guava 19.0    ：用于生成缓存，用在轮播图，视屏列表和视频详情

Lombok    	  ：自动生成Getter 和 Setter 方法

PageHelper    ：用于自动分页

Junit5			   ：生成自动单元测试

HttpClient	   ：编写http请求类，用于像微信后台发送请求

Gosn				：用于将对象转为json格式

Jackson			： 用于对象和Json相互转换

Zxing				：用于将URL转为二维码形式

### 开发部署环境

idea 2021+Macos

部署环境

Centos 7.0 + Nginx

### 实现功能

- 登录，注册，下单等功能'
- 前后端分离，完全采用Json数据格式交互
- 采用github接口oauth2协议授权认证登录
- 对于常用查询信息缓存，有效缓解并发压力
- 接入微信支付接口，可以使用微信支付



