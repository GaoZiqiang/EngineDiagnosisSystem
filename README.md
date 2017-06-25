# 发动机远程遥控与智能诊断系统
## 项目初衷
## 运行环境
wildfly10.0.0.Final + postgreSQL9.5.5 + eclipse
## 使用技术
Hibernate/JPA + Swing

maven构建
## 项目部署
1.搭建数据库

参见./doc/database/table_create.sql engine_info.sql

2.项目运行

在项目所在目录下运行命令mvn clean package wildfly:deploy
## 页面访问
在浏览器中输入'120.24.50.47/diagnosis'即可访问
