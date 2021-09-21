package cn.gyw.glearn.design.behaviour.interpreter;

/**
 * 解释器模式
 *
 * 编译器的开发
 * @author guanyw
 *
 */
public class InterpreterMainTest {
	public static void main(String[] args) {
		int result = new Minus().interpret(new Context(new Plus().interpret(new Context(9,2)),8));
		System.out.println(result);
	}
}
