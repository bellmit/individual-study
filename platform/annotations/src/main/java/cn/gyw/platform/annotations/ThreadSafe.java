package cn.gyw.platform.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识为线程安全
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {
        ElementType.TYPE,
        ElementType.METHOD
})
public @interface ThreadSafe {
}
