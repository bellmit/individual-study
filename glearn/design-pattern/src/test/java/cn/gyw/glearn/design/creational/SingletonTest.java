package cn.gyw.glearn.design.creational;

import cn.gyw.glearn.design.creational.singleton.HungrySingleton1;
import cn.gyw.glearn.design.creational.singleton.LazySingleton5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

/**
 * 单例模式的测试
 */
public class SingletonTest {

    /**
     * 单例模式通过反射创建新对象
     */
    @Test
    public void createAnotherSingle1BySetAccessible() {
        HungrySingleton1 hungrySingleton1 = HungrySingleton1.getInstance();

        try {
            Class<HungrySingleton1> singleton1Class = HungrySingleton1.class;
            Constructor<HungrySingleton1> constructor = singleton1Class.getConstructor();
            constructor.setAccessible(true);
            HungrySingleton1 another = constructor.newInstance();
            // 引用一致
            Assertions.assertFalse(another == hungrySingleton1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * CAS 构建单例
     */
    @RepeatedTest(5)
    public void getInstance() {
        LazySingleton5 singleton = LazySingleton5.getInstance();
        System.out.println(singleton);
    }
}
