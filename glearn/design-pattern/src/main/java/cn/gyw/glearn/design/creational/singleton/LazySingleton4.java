package cn.gyw.glearn.design.creational.singleton;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 双重检查锁
 */
@ThreadSafe
public class LazySingleton4 {

    // volatile 禁止指令重排
    private static volatile LazySingleton4 instance;

    public static LazySingleton4 getInstance() {
        if (instance == null) { // 一次判断，为null 尝试获取锁
            synchronized (instance) {
                if (instance == null) { // 二次判断是否为null
                    instance = new LazySingleton4();
                }
            }
        }
        return instance;
    }
}
