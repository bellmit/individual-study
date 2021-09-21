package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.NotThreadSafe;

/**
 * 懒汉
 */
@NotThreadSafe
public class LazySingleton1 {

    private static LazySingleton1 instance;

    public static LazySingleton1 getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new LazySingleton1();
        return instance;
    }

    private LazySingleton1() {}
}
