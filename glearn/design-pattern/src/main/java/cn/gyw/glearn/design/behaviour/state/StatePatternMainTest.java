package cn.gyw.glearn.design.behaviour.state;


public class StatePatternMainTest {
	public static void main(String[] args) {
//		 StateEnum se = StateEnum.valueOf("A");
//		 System.out.println(se);

//		 StateEnum se1 = EnumUtils.getEnumTypeByString(StateEnum.class, "getValue", "plan_E");
//		 System.out.println(se1);
//		Context con = new Context("plan_a");
//		State state = con.getState();
//		state.handle();

//		Context con = getContext("plan_c");
//		con.handle();
	}

	private static Context getContext(String eventName) {
		Context context = new Context();
		StateEnum state = EnumUtils.getEnumTypeByString(StateEnum.class, "getValue", eventName);
		switch(state) {
			case A :
				ConcreteStateA sa = new ConcreteStateA();
				sa.init(context);
				break;
			case B :
				ConcreteStateB sb = new ConcreteStateB();
				sb.init(context);
				break;
			case C :
				ConcreteStateC sc = new ConcreteStateC();
				sc.init(context);
				break;
			case D :
				ConcreteStateD sd = new ConcreteStateD();
				sd.init(context);
				break;
			default :
				throw new RuntimeException("checking the request param!");
		}
		return context;
	}
}
