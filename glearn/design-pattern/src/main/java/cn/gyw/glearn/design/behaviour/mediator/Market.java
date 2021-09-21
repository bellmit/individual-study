package cn.gyw.glearn.design.behaviour.mediator;

/**
 * 具体同事类：市场部
 * Created by guanyw on 2018/7/10.
 */
public class Market implements Department {

	private Mediator mediator;

	public Market(Mediator mediator) {
		this.mediator = mediator;

		mediator.register("market", this);
	}

	@Override
	public void selfAction() {
		System.out.println("市场部：正在跑市场......");
	}

	@Override
	public void outAction() {
		System.out.println("市场部向中介者(总经理)说：合同已搞定，需财务部报销.....");
		mediator.command("finacial");
	}
}
