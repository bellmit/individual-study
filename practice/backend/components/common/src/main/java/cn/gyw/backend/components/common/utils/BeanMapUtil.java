package cn.gyw.backend.components.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

public class BeanMapUtil {

	/**
	 * 将对象属性转化为map结合
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = new HashMap<>();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key + "", beanMap.get(key));
			}
		}
		return map;
	}

	/**
	 * 将map集合中的数据转化为指定对象的同名属性中
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
		T bean = clazz.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (int i = 0, len = pds.length; i < len; i++) {
			PropertyDescriptor pd = pds[i];
			String propName = pd.getName();
			if (map.containsKey(propName)) {
				try {
					Object value = map.get(propName);
					Field propField = getPrivateField(propName, clazz);
					if (propField == null) {
						continue;
					}
					propField.setAccessible(true);
					String propType = propField.getType().toString();
					if (propType.equals("class java.lang.String")) {
						propField.set(bean, value);
					} else if (propType.equals("class java.lang.Boolean")) {
						propField.set(bean, Boolean.parseBoolean(String.valueOf(value)));
					} else if (propType.equals("class java.lang.Long")) {
						propField.set(bean, Long.parseLong(String.valueOf(value)));
					} else if (propType.equals("class java.lang.Integer")) {
						propField.set(bean, Integer.parseInt(String.valueOf(value)));
					} else if (propType.equals("class java.lang.Double")) {
						propField.set(bean, Double.parseDouble(String.valueOf(value)));
					} else if (propType.equals("class java.lang.Float")) {
						propField.set(bean, Float.parseFloat(String.valueOf(value)));
					} else if (propType.equals("class java.math.BigDecimal")) {
						propField.set(bean, new BigDecimal(String.valueOf(value)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

	private static Field getPrivateField(String propName, Class<?> clazz) {
		try {
			return clazz.getDeclaredField(propName);
		} catch (NoSuchFieldException e) {
			if (clazz.getSuperclass() == null) {
				return null;
			}
		}
		return getPrivateField(propName, clazz.getSuperclass());
	}

	private BeanMapUtil() {
	}
}
