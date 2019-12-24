# blue-cat
---
## 说明
目前持续跟新中

### 系统说明

1. 使用Bearer Token的接口认证方式，结合spring security进行安全认证;
2. 使用阿里的Nacos注册中心和配置中心，系统内application.yaml的配置仅供文档备份不起实际作用，运行系统请部署Nacos（使用Nacos配置时注意系统使用的自定义命名空间和组）
3. 默认使用MariaDB作为主要数据库，使用redis作为默认缓存;
4. 由于文件分析和处理的文件结构的不确定性，目前使用mongo作为文件分析的数据库；
5. 使用spring cloud gateway作为网关；
6. docker-compose文件夹下为本系统使用的docker编译文件；
