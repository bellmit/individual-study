package cn.gyw.platform.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识类不可变，包含了@ThreadSafe的含义
 */
@Retention(value = RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Immutable {
}
