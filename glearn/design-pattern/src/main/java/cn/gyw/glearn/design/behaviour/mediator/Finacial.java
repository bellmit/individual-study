package cn.gyw.glearn.design.behaviour.mediator;

/**
 * 具体同事类：财务部
 * Created by guanyw on 2018/7/10.
 */
public class Finacial implements Department {

	private Mediator mediator;

	public Finacial(Mediator mediator) {
		this.mediator = mediator;

		mediator.register("finacial", this);
	}

	@Override
	public void selfAction() {
		System.out.println("财务部：会计正在核对财务......");
	}

	@Override
	public void outAction() {
		System.out.println("财务部向中介者(总经理)说：钱很多，花不完.....");
	}
}
