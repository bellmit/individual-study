package cn.gyw.glearn.design.behaviour.strategy;

/**
 * Created by guanyw on 2018/7/10.
 */
public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public Context() {
	}

	// 打印计算结果
	public void printCalculate(String exp) {
		System.out.println(strategy.calculate(exp));
	}

}
