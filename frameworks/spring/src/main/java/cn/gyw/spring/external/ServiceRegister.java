package cn.gyw.spring.external;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.gyw.spring.service.DataService;
import cn.gyw.spring.service.impl.CacheService;
import cn.gyw.spring.service.impl.DatabaseService;

/**
 * 通过配置字段 指定 DataService 的实现类
 *
 * @see cn.gyw.spring.service.DataService
 */
@Component(value = "serviceRegister")
public class ServiceRegister implements InitializingBean, ApplicationContextAware {

	private Map<Boolean, DataService> serviceImplMap = new HashMap<>();

	private ApplicationContext applicationContext;

	/**
	 * 获取接口实现类的所有bean，并按自己定的规则放入map中
	 * 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, DataService> serviceMap = applicationContext.getBeansOfType(DataService.class);
		// 以下代码是将bean按照自己定的规则放入map中，这里我的规则是key：true/false;value:DatabaseService/CacheService
		// 调用时，参数传入service.toString()的具体字符串就能获取到相应的bean
		// 此处也可以不做以下的操作，直接使用beanMap,在调用时，传入bean的名称
		for (DataService dataService : serviceMap.values()) {
			if (dataService.getClass().getName().equals(DatabaseService.class.getName())) {
				serviceImplMap.put(false, dataService);
			}
			if (dataService.getClass().getName().equals(CacheService.class.getName())) {
				serviceImplMap.put(true, dataService);
			}
		}
	}

	/**
	 * 获取 Spirng 的上下文
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 根据 是否开启缓存，获取不同的实现类
	 * 
	 * @param isCached
	 * @return
	 */
	public DataService getDataService(boolean isCached) {
		return serviceImplMap.get(isCached);
	}
}
