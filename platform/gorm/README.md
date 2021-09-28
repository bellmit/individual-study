# 手写ORM 框架
ORM：Object Relation mapping

# 现有框架
1. Hibernate：全自动，
2. Mybatis：半自动，需要手写SQL，支持简单映射，复杂关系需手动写
3. SpringJDBC：纯手写ORM框架，可定制
4. JPA：持久化的标准，基于注解开发

# 目的
1. Mybatis 手写sql 不可控
2. Hibernate 性能问题，入门容易，精通难
3. 从JDBC 重新封装一套比较费时间
4. 基于巨人的肩膀 SpringJDBC 二次开发封装ORM

# 做什么
约定大于配置
CRUD  
1. List<?> Page<?> select*(QueryRule queryRule)
2. int update*(T entity)
3. returnId insert*(T entity) entity ！= null
4. int delete*(T entity) entity 中id不为空，若id为空，其它必不为空

# 测试数据
```mysql
CREATE database IF NOT exists `demo_2021` CHARACTER SET utf8 COLLATE utf8_general_ci;
use demo_2021;

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `username` varchar(64) DEFAULT NULL COMMENT '用户名',
     `age` int DEFAULT 0 COMMENT '年龄',
     `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
     `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

```