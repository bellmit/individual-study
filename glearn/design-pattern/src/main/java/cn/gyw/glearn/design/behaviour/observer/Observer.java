package cn.gyw.glearn.design.behaviour.observer;

/**
 * 抽象观察者
 * 为所有观察者定义一个接口，在得到主题的通知时则更新自己。
 */
public interface Observer {
	void update(Subject subject);
}
