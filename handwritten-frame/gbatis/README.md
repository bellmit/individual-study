# 手写MyBatis 框架

# 类流程
SqlSession -> Configuration -> Executor

# 优化点
1. 参数预编译
2. 结果集映射
3. 插件扩展
4. 查询缓存
5. 注解方式配置SQL

# 准备sql
```sql
CREATE TABLE demo_2021.t_person (
	id INT auto_increment NOT NULL primary key,
	name varchar(100) NULL,
	age INT NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
```