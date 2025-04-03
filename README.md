<p align="center">
	<img alt="logo" width="196px" src="https://pic.imgdb.cn/item/66b9aaafd9c307b7e9a749fb.png">
</p>
<h1 align="center" style=" font-weight: bold;">t3rik-erp</h1>
<h4 align="center">专注于中小企业的erp系统</h4>
<p align="center">
<a href='https://gitee.com/wangsanping/t3rik-erp/stargazers'><img src='https://gitee.com/wangsanping/t3rik-erp/badge/star.svg?theme=dark' alt='star'></img></a>
        <a href="https://www.java.com/zh-CN/">
            <img src="https://img.shields.io/badge/java-17-red.svg" alt="java">
        </a> 
        <a href="https://spring.io/projects/spring-boot">
            <img src="https://img.shields.io/badge/spring--boot-3-green.svg" alt="spring-boot">
        </a>
        <a href="https://baomidou.com/">
            <img src="https://img.shields.io/badge/mybatis--plus-3.5.3.2-blue.svg" alt="mybatis-plus">
        </a> 
        <a href="https://kotlinlang.org/">
            <img src="https://img.shields.io/badge/kotlin-2.0-yellow.svg" alt="kotlin">
        </a> 
</p>

### 项目介绍

#### 技术架构: springboot3+jdk17+mybatis-plus+mysql8+kotlin+vue+uniapp+elementui等
#### 项目包括后台管理系统、手机端及pad端，手机端采用uniapp架构，可以随时编译为小程序

t3rik-erp项目是在苦糖果开源MES系统基础上进行二次开发的ERP系统，旨在解决中小企业在人力、物料、销售、生产制造等方面的信息化建设需求。该系统为中小企业提供了全面的信息化解决方案，帮助企业优化资源配置、提升生产效率，从而实现更高效、更智能的运营。

#### 演示地址

- 后台管理：http://www.t3rik.top/

- 移动端：http://www.t3rik.top:8000/
  - 当前是h5模式，请打开浏览器的调试工具选择移动设备模式查看

##### 路过点个star，后续在迭代业务代码的同时，会持续提供高质量代码，可以帮助你更好的学习Java

##### 联系请加qq群：9052204

#### 项目介绍视频陆续录制中...

 - 01-项目介绍：https://www.bilibili.com/video/BV1ZZ8meeE7U

#### 系统架构图

![](https://pic1.imgdb.cn/item/678145cfd0e0a243d4f327b7.jpg)

#### 功能升级列表

沿用若依vue的架构，并在若依vue的架构上进行了改造

以下是部分升级列表：

| jdk版本升级为jdk17                                           |
| ------------------------------------------------------------ |
| **springboot版本升级为3.0.6**                                |
| **集成了mybatis-plus框架及其提供的各种功能，如自动填充等功能** |
| **集成Redisson客户端，几行代码就可以使用分布式锁**           |
| **集成积木报表**                                             |
| **mybatis插件方式实现数据脱敏**                              |
| **代码生成可以选择生成mybatis-plus风格的代码**               |
| **可以和kotlin进行混编，可以使用协程**                       |
| **druid升级springboot3版本**                                 |
| **SpringSecurity升级为springboot3版本**                      |
| **jjwt升级为0.12.3,替换过期方法**                            |

#### 业务功能介绍

当前版本在苦糖果MES基础上增加了如下功能

| 功能                   | 说明                                                         |
| ---------------------- | ------------------------------------------------------------ |
| **客户订单**           | **可以通过客户订单，生成生产工单，在生产工单上层，先创建客户订单，兼容各行业不同的规格，客户订单层面生成生产工单后自动跳转到生产工单页面，同时对当前产品进行bom组成的添加。** |
| **生产报工**           | **重做报工功能，在报工的同时可以进行退料操作及废料操作。**   |
| **生产派单**           | **可以在创建任务后对任务进行派单操作。**                     |
| **人力资源**           | **目前已完成：职级管理、面试邀请、入职申请、入职审核。**     |
| **销售订单**           | **确认销售单后，可以推送至生产单**                           |
| **销售送货单**         | **物流相关**                                                 |
| **采购订单**           | **采购相关**                                                 |
| **报表**               | **数据报表、图形报表、打印**                                 |
| **移动app模块**        | **与后端权限完成集成，目前可以根据角色进行不同菜单的加载。** |
| **前端组件优化**       | **例如：添加选择产品组件，支持在添加产品的同时可以对产品进行维护。** |
| **功能调整及代码重构** | **例如：通过反射重构仓库模块中的库区和库位功能代码、使用库存操作使用分布式锁、mybatis插件方式实现数据脱敏等。** |

#### 安装教程

1.  **使用doc目录下的sql文件进行数据库初始化操作，数据库使用MySQL**
2.  **后端项目**
    1.  **项目采用Maven构建**
    2.  **jdk版本设置为17**
    3.  **Kotlin版本为2.0**
    4.  **默认使用dev配置文件，需修改其中的mysql连接地址和redis连接地址为自己服务器地址**
    5.  **编译成功后，找到`t3rik-erp`模块中的ERPApplication类运行其`main`方法**

3.  **前端项目**
    1.  **前端项目采用Vue2+Webpack构建**
    2.  **node版本为16.13+**
    3.  **使用vscode打开项目，在控制台输入`npm install`，先进行项目的依赖包安装，如有网络问题，可以尝试使用`cnpm install`**
    4.  **安装完成后，输入命令` npm run dev`，启动项目**

4.  **移动项目**
    1.  **项目使用uniapp构建。**
    2.  **使用`HbuilderX`打开项目，选择项目运行。**


#### 代码结构

- **t3rik-erp-parent**
  - **后端Java和kotlin项目**
  - **其中erp模块为启动入口模块**
  - **mes为生产系统业务**
  - **mobile为手机和移动端业务**
  - **hrm为人资模块**
  - **report为报表模块**
- **t3rik-erp-ui**
  - **后台管理前端**
  - **地址：https://gitee.com/wangsanping/t3rik-erp-ui**
- **t3rik-app**
  - **app前端**
  - app端代码暂未开源，如需获取，请star后通过app端演示项目中的源码下载模块获取


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
- 销售单
- ![](https://pic1.imgdb.cn/item/6782965ad0e0a243d4f37849.png)
- ![](https://pic1.imgdb.cn/item/6782965ad0e0a243d4f3784a.png)
- 报表
- ![](https://pic1.imgdb.cn/item/6782965ad0e0a243d4f37847.png)
- 人资-入职审核
- ![](https://pic1.imgdb.cn/item/6782965ad0e0a243d4f37848.png)
- 人资-职级管理
- ![](https://pic1.imgdb.cn/item/6782965ad0e0a243d4f3784b.png)
- 移动端截图

![](https://pic.imgdb.cn/item/66b0ec5cd9c307b7e99c0a4d.png) ![](https://pic.imgdb.cn/item/66b0ec5cd9c307b7e99c0a61.png)

![](https://pic.imgdb.cn/item/66c58db3d9c307b7e9fdc24f.png) ![](https://pic.imgdb.cn/item/66c58d62d9c307b7e9fd7993.png)

![](https://pic.imgdb.cn/item/66c58d62d9c307b7e9fd7946.png)


感谢 苦糖果mes：https://gitee.com/kutangguo/ktg-mes.git，若依：https://gitee.com/y_project/RuoYi-Vue.git