# shcpemonitor
（一）项目介绍
    上海票交所模拟器是针对银行多套测试环境情况模拟人行向行内发起和应答报文、模拟对手行向行内发起需要的报
文，实现不同的测试场景的测试需求。
#
（二）技术选型
   通过本项目实战加深对各框架理解。
   1、后端 
   核心框架：Springboot
   安全框架：Apache Shiro 1.2
   持久层框架：MyBatis 3.2
   数据库连接池：Alibaba Druid 1.0
   缓存框架：memcached、Redis
   日志管理：SLF4J 1.7、Log4j
   分布式框架： zookeeper dubbo 分布式锁
   工具类：Apache Commons、Jackson 2.2、Xstream 1.4、Dozer 5.3、POI 3.9
   
   2、前端
   暂时用jsp,后面替换成vue.js 或者angular.js
（三）后续计划
    功能初步实现后，将项目按模块拆分多个子项目，支持分布式部署，往微服务方向发展，集成dobble+zk
