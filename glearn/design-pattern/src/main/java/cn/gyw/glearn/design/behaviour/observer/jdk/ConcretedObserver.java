package cn.gyw.glearn.design.behaviour.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * java.support.Observer接口
 * 具体观察者
 * Created by guanyw on 2018/7/10.
 */
public class ConcretedObserver implements Observer {

	private int state;

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ConcretedSubject) {
			state = ((ConcretedSubject)o).getState();
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
