package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 饿汉式单例：当类一加载就直接new一个静态实例。
 */
@ThreadSafe
public class HungrySingleton1 {

	/*
	私有化构造函数
	 */
	private HungrySingleton1() {}

	/*
	静态变量
	类一加载，就new一个实例出来，从属这个类
	PS:享有特权的客户端可以借助AccessibleObject.setAccessible方法，通过反射机制调用私有构造器，
	抵御这种攻击可以修改构造器，在第二次创建时抛出异常。
	 */
	private static final HungrySingleton1 singleton = new HungrySingleton1();

	/*
	共有 静态方法获取单例
	 */
	public static HungrySingleton1 getInstance() {
		return singleton;
	}

}
