package cn.gyw.handwritten.gspring.context;

/**
 * 通过解耦方式获取IOC容器的顶层设计
 * 
 * 通过监听器扫描所有的类，实现该接口，自动调用set方法
 */
public interface GApplicationContextAware {

	void setApplicationContext(GApplicationContext applicationContext);
}
