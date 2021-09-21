package cn.gyw.glearn.design.behaviour.observer;

/**
 * 具体观察者
 */
public class ConcretedObserver implements Observer {

	// 需要和subject中的state状态一致
	private int state;

	@Override
	public void update(Subject subject) {
		System.out.println("observer1 has received...");
		System.out.println("执行 相应的操作 state:" + subject.operation(null));
		this.state = (int) subject.operation(null);
	}

	public ConcretedObserver() {
	}

	public ConcretedObserver(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
