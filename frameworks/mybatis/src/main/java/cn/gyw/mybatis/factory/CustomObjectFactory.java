package cn.gyw.mybatis.factory;

import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import cn.gyw.mybatis.model.Phone;

/**
 * 对象工厂
 */
public class CustomObjectFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = 1L;

	@Override
	public <T> T create(Class<T> type) {
		System.out.println("this is PhoneObjectFactory");
		T instance = super.create(type);
		if (type == Phone.class) {
			Phone phone = (Phone) instance;
			phone.setName(phone.getName() + ">>666");
		}
		return instance;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		
		properties.keySet().forEach(key -> {
			System.out.println(key + " : " + properties.getProperty(key.toString()));
		});
	}
}
