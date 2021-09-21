package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 懒汉 方法同步
 *
 */
@ThreadSafe
public class LazySingleton2 {

	private static LazySingleton2 instance;

	private LazySingleton2(){}

	/*
	方法同步，高并发的情况下，效率不高
	 */
	public static synchronized LazySingleton2 getInstance() {
		if(instance == null) {
			instance = new LazySingleton2();
		}
		return instance;
	}
}
