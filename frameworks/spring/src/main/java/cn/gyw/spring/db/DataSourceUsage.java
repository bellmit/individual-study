package cn.gyw.spring.db;

import cn.gyw.spring.db.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解 数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceUsage {

    DataSourceType value() default DataSourceType.MASTER;
}
