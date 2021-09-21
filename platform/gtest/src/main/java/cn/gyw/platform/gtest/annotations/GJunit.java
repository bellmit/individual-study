package cn.gyw.platform.gtest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Junit 测试接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GJunit {

    /**
     * 自定义拦截器类数组
     * @return
     */
    Class<?>[] values();
}
