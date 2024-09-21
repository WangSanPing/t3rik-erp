<p align="center">
	<img alt="logo" width="196px" src="https://pic.imgdb.cn/item/66b9aaafd9c307b7e9a749fb.png">
</p>
<h1 align="center" style=" font-weight: bold;">t3rik-erp</h1>
<h4 align="center">专注于中小企业的erp系统</h4>
<p align="center">
<a href='https://gitee.com/wangsanping/t3rik-erp/stargazers'><img src='https://gitee.com/wangsanping/t3rik-erp/badge/star.svg?theme=dark' alt='star'></img></a>
        <a href="https://spring.io/projects/spring-boot">
            <img src="https://img.shields.io/badge/spring-boot-3-green.svg" alt="spring-boot">
        </a>
        <a href="https://baomidou.com/">
            <img src="https://img.shields.io/badge/mybatis-plus-3.5.3.2-blue.svg" alt="mybatis-plus">
        </a> 
</p>

### 项目介绍

#### 技术架构: springboot3+jdk17+mybatis-plus+mysql8+kotlin+vue+uniapp+elementui等
#### 项目包括后台管理系统、手机端及pad端，手机端采用uniapp架构，可以随时编译为小程序

t3rik-erp项目是在苦糖果开源MES系统基础上进行二次开发的ERP系统，旨在解决中小企业在人力、物料及生产制造等方面的信息化建设需求。该系统为中小企业提供了全面的信息化解决方案，帮助企业优化资源配置、提升生产效率，从而实现更高效、更智能的运营。

##### 路过点个star，后续在迭代业务代码的同时，会持续提供高质量代码，可以帮助你更好的学习Java

##### 联系请加qq群：9052204

#### 演示地址

- 后台管理：https://www.t3rik.top/

- 移动端：http://1.92.89.102:8000/
  - 当前是h5模式，请打开浏览器的调试工具选择移动设备模式查看

#### 项目介绍视频陆续录制中...

 - 01-项目介绍：https://www.bilibili.com/video/BV1ZZ8meeE7U

#### 软件架构

沿用若依vue的架构，并在若依vue的架构上进行了部分改造

- jdk版本升级为jdk17
- springboot版本升级为3.0.6
- 集成了mybatis-plus框架及其提供的，如自动填充等功能
- 代码生成可以选择生成mybatis-plus风格的代码
- 可以和kotlin进行混编
- fastjson升级为fastjson2
- druid升级springboot3版本
- mybatis-plus升级为springboot3版本
- jjwt升级为0.12.3,替换过期方法
- kotlin可以使用协程

#### 主要功能介绍

当前版本在苦糖果MES基础上增加了

- 客户订单
  - 可以通过客户订单，生成生产工单，在生产工单上层，先创建客户订单，兼容各行业不同的规格
  - 客户订单层面生成生产工单后自动跳转到生产工单页面，同时对当前产品进行bom组成的添加
- 生产报工
  - 重做报工功能，在报工的同时可以进行退料操作及废料操作
- 生产派单
  - 可以在创建任务后对任务进行派单操作
- 人力资源池
  - 职级管理
- 添加选择产品组件，支持在添加产品的同时可以对产品进行维护
- 添加移动app模块，目前可以登录及根据角色进行不同菜单的加载
- 操作易用性修改
- 功能调整及代码重构



#### 安装教程

1.  使用doc目录下的sql文件进行数据库初始化操作

#### 代码结构

- t3rik-erp-parent
  - 后端Java和kotlin项目
  - 其中erp模块为启动入口模块
  - mes为生产系统业务
  - mobile为手机和移动端业务
- t3rik-erp-ui
  - 后台管理前端
  - 地址：https://gitee.com/wangsanping/t3rik-erp-ui
- t3rik-erp-pad
  - pad前端
  - https://gitee.com/wangsanping/t3rik-erp-pad
- t3rik-app
  - app前端
  - 如需源码，请在移动端演示地址中，登录下载：http://1.92.89.102:8000/
  - 目前持续开发中，开源不易，请star支持，感谢。

#### 部分功能截图

- 客户订单
  - 添加物料需求后可实时查看库存
![库存](https://pic.imgdb.cn/item/668a3392d9c307b7e9c920a7.png)
  - 重写选择组件，产品选择页面可直接添加产品
![产品](https://pic.imgdb.cn/item/668a3402d9c307b7e9c9c5a5.png)
  - 新增后可识别产品物料需求，自动添加
![产品](https://pic.imgdb.cn/item/668a3392d9c307b7e9c920a7.png)
  - 可在信息填写完成后，生成生产工单
![产品](https://pic.imgdb.cn/item/668a34e5d9c307b7e9cafa54.png)
- 生产报工
  - 重做成产报工功能，在报工的同时可以添加当前任务的退料及废料
![报工](https://pic.imgdb.cn/item/668a3666d9c307b7e9cd1a69.png)
- 生产派单
  - 生产任务新建完成后，可选择派单到某责任人，支持按订单分组
![派单](https://pic.imgdb.cn/item/668a3533d9c307b7e9cb6795.png)
![派单](https://pic.imgdb.cn/item/668a354fd9c307b7e9cb898f.png)
- 移动端截图

![](https://pic.imgdb.cn/item/66b0ec5cd9c307b7e99c0a4d.png) ![](https://pic.imgdb.cn/item/66b0ec5cd9c307b7e99c0a61.png)

![](https://pic.imgdb.cn/item/66c58db3d9c307b7e9fdc24f.png) ![](https://pic.imgdb.cn/item/66c58d62d9c307b7e9fd7993.png)

![](https://pic.imgdb.cn/item/66c58d62d9c307b7e9fd7946.png)


感谢 苦糖果mes：https://gitee.com/kutangguo/ktg-mes.git，若依：https://gitee.com/y_project/RuoYi-Vue.git