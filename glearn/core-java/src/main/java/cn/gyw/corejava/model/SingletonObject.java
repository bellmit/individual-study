package cn.gyw.corejava.model;

/**
 * 饿汉式单例：当类一加载就直接new一个静态实例。
 */
public class SingletonObject {

	/*
	私有化构造函数
	 */
	private SingletonObject() {}

	/*
	静态变量
	类一加载，就new一个实例出来，从属这个类
	PS:享有特权的客户端可以借助AccessibleObject.setAccessible方法，通过反射机制调用私有构造器，
	抵御这种攻击可以修改构造器，在第二次创建时抛出异常。
	 */
	private static final SingletonObject singleton = new SingletonObject();

	/*
	共有 静态方法获取单例
	 */
	public static SingletonObject getInstance() {
		return singleton;
	}

}
