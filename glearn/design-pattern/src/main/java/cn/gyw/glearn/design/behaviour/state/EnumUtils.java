package cn.gyw.glearn.design.behaviour.state;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {

	/**
	 * Enum<String>
	 * 利用反射，根据[值]返回Enum的示例
	 * @param clz Enum类
	 * @param getMethodName get方法名
	 * @param arg0 值
	 * @return
	 */
	public static <T extends Enum<T>> T getEnumTypeByString(Class<T> clz , String getMethodName , String arg0) {
		T result = null;
		try {
			T[] tr = clz.getEnumConstants();
			Method readMethod = clz.getDeclaredMethod(getMethodName);
			String arg = null;
			for (T entity : tr) {
				arg = readMethod.invoke(entity).toString();
				if(arg.equals(arg0)) {
					result = entity;
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return result;
	}
}
