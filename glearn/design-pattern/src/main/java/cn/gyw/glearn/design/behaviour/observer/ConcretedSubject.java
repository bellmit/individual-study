package cn.gyw.glearn.design.behaviour.observer;


public class ConcretedSubject extends AbstractSubject {

	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		//当修改了主题对象状态时，通知所有观察者
		this.state = state;

		this.notifyAllObserver();
	}

	@Override
	public Object operation(Object param) {
		if (param != null) {
			setState((Integer) param);
		}
		return state;

	}
}
