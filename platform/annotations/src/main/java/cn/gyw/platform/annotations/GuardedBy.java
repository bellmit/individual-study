package cn.gyw.platform.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识每个需要加锁的状态变量以及保护该变量的锁，有助于代码的维护和审查，通过自动化工具找出潜在线程安全性错误。
 *
 * 使用方法：
 * 在使用加锁的类时，应说明
 * 1）哪些状态变量是由哪些锁保护的
 * 2）哪些锁被用于保护这些变量
 *
 * 示例：
 * @GuardedBy(lock)：只有在持有了某个特定的锁时才能访问这个域或方法。
 * @GuradedBy("this")：对象上的内置锁
 *
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface GuardedBy {

    String value() default "";

}
