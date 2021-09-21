package cn.gyw.glearn.design.behaviour.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：具体主题
 */
public abstract class AbstractSubject implements Subject {

	// 保存该主题下的所有Observer
	private List<Observer> oList = new ArrayList<>();

	@Override
	public void registerObserver(Observer observer) {
		oList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		oList.remove(observer);
	}

	@Override
	public void notifyAllObserver() {
		for (Observer observer : oList) {
			observer.update(this);
		}
	}

}
