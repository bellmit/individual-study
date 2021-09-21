package cn.gyw.glearn.design.behaviour.observer;

/**
 * 观察者模式：抽象主题
 * 例如：
1、游戏中的例子：游戏中，当进入新人的时候，大喇叭会通知所有的玩家。
 	所有的游戏玩家是订阅者，而大喇叭就是一个发布者。
2、QQ群聊天中：在群里聊天的时候，你发一句话，大家都可以看到。
 	那么，这个群里的人就是订阅者，而通过群发消息的服务器则作为了一个发布者。
 */

public interface Subject {

	// 添加观察者
	void registerObserver(Observer observer);

	// 删除观察者
	void removeObserver(Observer observer);

	// 通知所有观察者
	void notifyAllObserver();

	Object operation(Object param);
}
