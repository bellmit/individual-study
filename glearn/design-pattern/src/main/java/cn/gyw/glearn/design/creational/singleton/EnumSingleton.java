package cn.gyw.glearn.design.creational.singleton;

/**
 * 枚举实现单例模式
 *
 * 优点：避免串行化和反射攻击
 * 缺点：不能继承
 */
public enum EnumSingleton {

	/**
	 * 定义一个枚举元素，本身就是一个单例的实例了
	 */
	INSTANCE;

	public void singletonOperate() {
		System.out.println("invoke");
	}

}
