package cn.gyw.corejava.jdk.jdk8.defaultinterface;

/**
 * 接口默认方法
 *
 * 1、接口的属性 都是final
 * 2、方法都为public
 * 3、不允许构造函数
 */
public interface InterfaceDefaultMethod {

	// javap 查看字节码，是public static final 修饰
	int a = 0;

	void execute();

	default int add(int x, int y) {
		System.out.println("Parent InterfaceDefaultMethod");
		return x + y;
	}

	default double add(double x, double y) {
		return x + y;
	}

	default int div(int x, int y) {
		return x - y;
	}

}
