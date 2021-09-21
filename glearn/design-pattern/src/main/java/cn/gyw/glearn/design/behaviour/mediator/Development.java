package cn.gyw.glearn.design.behaviour.mediator;

/**
 * 具体同事类：研发部
 * Created by guanyw on 2018/7/10.
 */
public class Development implements Department {

	// 中介者：总经理
	private Mediator mediator;

	public Development(Mediator mediator) {
		this.mediator = mediator;
		// 中介者对象需要知道所有的具体同事类（部门）
		mediator.register("development", this);
	}

	@Override
	public void selfAction() {
		System.out.println("研发部：研发部正在做研发....");
	}

	@Override
	public void outAction() {
		System.out.println("研发部向中介者(总经理)say：研发经费不够了...");
	}
}
