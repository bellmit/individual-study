package cn.gyw.backend.components.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedSetValueField {
	
	/**
	 * 获取目标数据的类 class
	 * @return
	 */
	Class<?> beanClass();
	
	/**
	 * 获取目标数据的 方法
	 * @return
	 */
	String method();
	
	/**
	 * 获取目标数据的参数
	 * @return
	 */
	String param();
	
	/**
	 * 从已获取目标数据中取值字段
	 * @return
	 */
	String targetField();

}
