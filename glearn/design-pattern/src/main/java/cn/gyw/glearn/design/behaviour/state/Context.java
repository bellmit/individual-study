package cn.gyw.glearn.design.behaviour.state;

public class Context {

	private State state;

	Context() {}

	Context(String arg0) {
		StateEnum se = EnumUtils.getEnumTypeByString(StateEnum.class, "getValue", arg0);
		if (StateEnum.A.equals(se)) {
			state = new ConcreteStateA();
		}
		if (StateEnum.B.equals(se)) {
			state = new ConcreteStateB();
		}
		if (StateEnum.C.equals(se)) {
			state = new ConcreteStateC();
		}
		if (StateEnum.D.equals(se)) {
			state = new ConcreteStateD();
		}
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void handle() {
		this.state.handle();
	}
}
