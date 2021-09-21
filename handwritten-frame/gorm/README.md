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
1. select*(QueryRule queryRule)
2. update*(T entity)
3. insert*(T entity) entity ！= null
4. delete*(T entity) entity 中id不为空，若id为空，其它必不为空