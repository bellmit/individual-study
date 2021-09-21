package cn.gyw.glearn.design.behaviour.templatemethod;

/**
 * Created by guanyw on 2018/7/10.
 */
public class DrawMoney extends BankTemplateMethod {
	@Override
	public void transact() {
		System.out.println("我要取款");
	}
}
