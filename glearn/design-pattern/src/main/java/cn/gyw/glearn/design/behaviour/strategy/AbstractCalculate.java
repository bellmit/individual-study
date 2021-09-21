package cn.gyw.glearn.design.behaviour.strategy;

public abstract class AbstractCalculate implements Strategy {

	public int[] split(String exp,String opt) {
		int[] p = new int[2];
		String[] params = exp.split(opt);
		p[0] = Integer.parseInt(params[0]);
		p[1] = Integer.parseInt(params[1]);

		return p;
	}
}
