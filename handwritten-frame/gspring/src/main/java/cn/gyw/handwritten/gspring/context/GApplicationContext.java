package cn.gyw.handwritten.gspring.context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import cn.gyw.handwritten.gspring.annotation.GAutowired;
import cn.gyw.handwritten.gspring.annotation.GController;
import cn.gyw.handwritten.gspring.annotation.GService;
import cn.gyw.handwritten.gspring.aop.config.GAopConfig;
import cn.gyw.handwritten.gspring.aop.framework.GAdvisedSupport;
import cn.gyw.handwritten.gspring.aop.framework.GAopProxy;
import cn.gyw.handwritten.gspring.aop.framework.GCglibAopProxy;
import cn.gyw.handwritten.gspring.aop.framework.GJdkDynamicAopProxy;
import cn.gyw.handwritten.gspring.beans.GBeanWrapper;
import cn.gyw.handwritten.gspring.beans.GBeanFactory;
import cn.gyw.handwritten.gspring.beans.factory.GListableBeanFactory;
import cn.gyw.handwritten.gspring.beans.factory.config.GBeanDefinition;
import cn.gyw.handwritten.gspring.beans.factory.config.GBeanPostProcessor;
import cn.gyw.handwritten.gspring.beans.factory.support.GBeanDefinitionReader;
import cn.gyw.handwritten.gspring.beans.factory.support.GDefaultListableBeanFactory;

/**
 * IOC -> DI -> MVC -> AOP
 */
public class GApplicationContext extends GDefaultListableBeanFactory implements GListableBeanFactory, GBeanFactory {

    private String[] configLocations;

    // 通用的IOC容器
    /**
     * Cache of unfinished FactoryBean instances: FactoryBean name --> BeanWrapper
     */
    private final Map<String, GBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

    // 单例IOC容器缓存
    /**
     * Cache of singleton objects: bean name --> bean instance
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** Cache of singleton factories: bean name --> ObjectFactory */
    //private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /**
     * Cache of early singleton objects: bean name --> bean instance
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    
    private GBeanDefinitionReader reader;

    public GApplicationContext() {
        this("");
    }

    // 参考 ClassPathXmlApplicationContext
    public GApplicationContext(String... configLocations) {
        if (configLocations != null) {
            this.configLocations = new String[configLocations.length];
            for (int i = 0, len = configLocations.length ; i < len ; i++) {
                this.configLocations[i] = configLocations[i];
            }
        } else {
            this.configLocations = null;
        }
        this.refresh();
    }

    @Override
    protected void refresh() {
        try {
            // IOC 流程
            // 1. 定位，定位配置文件
            reader = new GBeanDefinitionReader();

            // 2. 加载配置文件，扫描相关的类，把它们封装为BeanDefinition
            List<GBeanDefinition> beanDefinitions = reader.loadBeanDefinitions(this.configLocations);

            // 3. 注册，将配置信息放到容器里面
            doRegisterBeanDefinition(beanDefinitions);

            // 4. 把不是延迟加载的类，提前初始化
            doAutowired();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 只处理非延迟加载的情况
    private void doAutowired() throws Exception {
        for (Map.Entry<String, GBeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            if (!entry.getValue().isLazyInit()) { // 非懒加载
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<GBeanDefinition> beanDefinitions) {
        GBeanDefinition beanDefinition;
        for (int i = 0, len = beanDefinitions.size(); i < len; i++) {
            beanDefinition = beanDefinitions.get(i);
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    /**
     * DI
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) throws Exception {
        Object instance = null;
        // 前置通知器，TODO: 工厂模式+策略模式，根据instance是否包含aware接口，调用相关的通知器
        GBeanPostProcessor beanPostProcessor = new GBeanPostProcessor();
        beanPostProcessor.postProcessBeforeInitialization(instance, beanName);

        // DI，初始化和注入分开，解决循环依赖问题
        // 1. 初始化
        GBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        instance = instantiateBean(beanName, beanDefinition);

        // 对象封装为GBeanWrapper
        GBeanWrapper beanWrapper = new GBeanWrapper(instance);

        // 2. 拿到GBeanWrapper 放入IOC容器中
        //if (this.factoryBeanInstanceCache.containsKey(beanName)) {
        //	throw new Exception("The " + beanName + " is exists!");
        //}
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        beanPostProcessor.postProcessAfterInitialization(instance, beanName);

        // 3. 依赖注入
        populateBean(beanName, beanDefinition, beanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    private void populateBean(String beanName, GBeanDefinition gBeanDefinition, GBeanWrapper gBeanWrapper) {
        Object instance = gBeanWrapper.getWrappedInstance();
        Class<?> clazz = gBeanWrapper.getWrappedClass();
        // 判断只有注解的属性才需要注入
        if (!(clazz.isAnnotationPresent(GController.class) || clazz.isAnnotationPresent(GService.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(GAutowired.class)) {
                continue;
            }
            GAutowired gAutowired = field.getAnnotation(GAutowired.class);
            String autowiredBeanName = gAutowired.value();
            if ("".equals(autowiredBeanName)) { // 若未指定BeanName，使用全类名
                autowiredBeanName = field.getType().getName();
            }
            // 设置可访问
            field.setAccessible(true);
            try {
                Object wrappedInstance = this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance();
                if (Objects.isNull(wrappedInstance)) {
                    continue;
                }
                field.set(instance, wrappedInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, GBeanDefinition gBeanDefinition) {
        // 1. 拿到实例化对象类名
        String className = gBeanDefinition.getBeanClassName();
        Object instance = null;

        // 2. 反射创建对象实例
        try {
            if (this.singletonObjects.containsKey(className)) {
                instance = this.singletonObjects.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                GAdvisedSupport proxyConfig = instantionAopConfig(gBeanDefinition);
                proxyConfig.setTargetClass(clazz);
                proxyConfig.setTarget(instance);
                // 切点匹配的话，创建代理对象
                if (proxyConfig.pointCutMatch()) {
                    instance = createProxy(proxyConfig).getProxy();
                }
                
                // 类型 -> 实例
                this.singletonObjects.put(className, instance);
                // beanName -> 实例
                this.singletonObjects.put(gBeanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private GAopProxy createProxy(GAdvisedSupport proxyConfig) {
        Class<?> targetClass = proxyConfig.getTargetClass();
        if (targetClass.getInterfaces().length > 0) {
            return new GJdkDynamicAopProxy(proxyConfig);
        }
        return new GCglibAopProxy(proxyConfig);
    }

    private GAdvisedSupport instantionAopConfig(GBeanDefinition gBeanDefinition) {
        GAopConfig aopConfig = new GAopConfig();
        aopConfig.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        aopConfig.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        aopConfig.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        aopConfig.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        aopConfig.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        aopConfig.setAspectThrowExceptionName(this.reader.getConfig().getProperty("aspectThrowExceptionName"));
        return new GAdvisedSupport(aopConfig);
    }

    @Override
	public int getBeanDefinitionCount() {
		return this.beanDefinitionMap.size();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
	}
	
	public Properties getConfig() {
		return this.reader.getConfig();
	}
}
