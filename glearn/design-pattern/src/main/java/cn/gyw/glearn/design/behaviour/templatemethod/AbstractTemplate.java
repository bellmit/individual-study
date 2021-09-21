package cn.gyw.glearn.design.behaviour.templatemethod;

public abstract class AbstractTemplate {

	/*主方法，实现对本类其它方法的调用*/
	public final int calculate(String exp, String opt) {
		int[] params = split(exp, opt);
		return calculate(params[0], params[1]);
	}

	public static int[] split(String exp,String opt) {
		String[] params = exp.split(opt);
		int[] result = new int[2];
		result[0] = Integer.parseInt(params[0]);
		result[1] = Integer.parseInt(params[1]);
		return result;
	}

	public abstract int calculate(int p1, int p2);
}
