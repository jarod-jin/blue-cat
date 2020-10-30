# blue-cat
---
## 说明
目前持续跟新中

### 技术栈说明

1. 使用Bearer Token的接口认证方式，结合spring security进行安全认证;
2. 使用阿里的Nacos注册中心和配置中心，系统内application.yaml的配置仅供文档备份不起实际作用，运行系统请部署Nacos（使用Nacos配置时注意系统使用的自定义命名空间和组）
3. 默认使用MariaDB作为主要数据库，使用redis作为默认缓存，MongoDB作为用户权限相关的存储数据库;
4. 由于文件分析和处理的文件结构的不确定性，目前使用mongo作为文件分析的数据库；
5. 使用spring cloud gateway作为网关；
6. docker-compose文件夹下为本系统使用的docker编译文件，运行命令为docker-compose -f xxx.yml up -d；

### 模块说明

#### core为基础依赖

1. 集成spring cloud feign api的
2. 集成系统通用的util工具集合
3. 集成基础config配置

#### utility为基础功能模块

1. 提供邮件服务
2. 提供字典
3. 系统操作日志

#### gateway为网关

基于spring cloud gateway改写，提供系统核心路由服务

#### oauth为鉴权中心

基于spring security oauth2.0改写系统系统核心认证服务
