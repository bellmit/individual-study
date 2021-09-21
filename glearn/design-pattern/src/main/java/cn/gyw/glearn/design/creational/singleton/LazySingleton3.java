package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 静态内部类创建单例
 */
@ThreadSafe
public class LazySingleton3 {
	 /* 私有构造方法，防止被实例化 */
	private LazySingleton3() {

	}
	/* 此处使用一个内部类来维护单例 */
	private static class SingletonFactory {
		private final static LazySingleton3 instance = new LazySingleton3();
	}

	public static LazySingleton3 getSingleton() {
		return SingletonFactory.instance;
	}
	 /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return getSingleton();
	}
}
