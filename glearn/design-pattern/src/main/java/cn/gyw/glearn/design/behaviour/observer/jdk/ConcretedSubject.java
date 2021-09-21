package cn.gyw.glearn.design.behaviour.observer.jdk;

import java.util.Observable;

/**
 * JDK 实现的java.support.Observable，目标对象
 * Created by guanyw on 2018/7/10.
 */
public class ConcretedSubject extends Observable {

	private int state;

	public void updateState(int st) {
		state = st;
		// 调用父类方法，标识目标对象发生了变化
		setChanged();
		notifyObservers(state);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
