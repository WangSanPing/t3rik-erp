# t3rik-erp-opensource

#### 介绍

t3rik-erp项目是在苦糖果开源MES系统基础上进行二次开发的ERP系统，旨在解决中小企业在人力、物料及生产制造等方面的信息化建设需求。该系统为中小企业提供了全面的信息化解决方案，帮助企业优化资源配置、提升生产效率，从而实现更高效、更智能的运营。

路过点个star，后续在迭代业务代码的同时，会持续提供高质量代码，可以帮助你更好的学习Java
联系请加qq群：9052204

#### 演示地址

http://124.222.107.179/

#### 主要功能介绍

当前版本在苦糖果MES基础上增加了

- 客户订单
  - 可以通过客户订单，生成生产工单，在生产工单上层，先创建客户订单，兼容各行业不同的规格
  - 客户订单层面生成生产工单后自动跳转到生产工单页面，同时对当前产品进行bom组成的添加
- 生产报工
  - 重做报工功能，在报工的同时可以进行退料操作及废料操作
- 添加选择产品组件，支持在添加产品的同时可以对产品进行维护
- 添加移动app模块，目前可以登录及根据角色进行不同菜单的加载
- 操作易用性修改
- 功能调整及代码重构

#### 软件架构

沿用若依vue的架构，并在若依vue的架构上进行了部分改造

- jdk版本升级为jdk17
- springboot版本升级为2.7.18
- 集成了mybatis-plus框架及其提供的，如自动填充等功能
- 代码生成可以选择生成mybatis-plus风格的代码
- 可以和kotlin进行混编
- 部分功能修改


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
- t3rik-app
  - app前端
  - 地址：https://gitee.com/wangsanping/t3rik-app



感谢 苦糖果mes：https://gitee.com/kutangguo/ktg-mes.git，若依：https://gitee.com/y_project/RuoYi-Vue.git