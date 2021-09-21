package cn.gyw.corejava.jdk;

import org.junit.Test;

import cn.gyw.corejava.jdk.jdk8.defaultinterface.DefaultInterfaceExtends1;
import cn.gyw.corejava.jdk.jdk8.defaultinterface.DefaultInterfaceExtends2;
import cn.gyw.corejava.jdk.jdk8.defaultinterface.InterfaceDefaultMethod;

/**
 * 默认接口的测试
 */
public class DefaultInterfaceTest {

    /**
     * 单个接口默认方法覆盖问题
     */
    @Test
    public void testDefaultMethodOverride() {
        DefaultIntefaceImpl impl = new DefaultIntefaceImpl();

        impl.add(10, 20);
    }

    /**
     * 多个接口默认方法实现，当前类必须重写重名的默认方法
     */
    @Test
    public void doubleDefaultMethod() {
        DefaultIntefaceImpl2 impl = new DefaultIntefaceImpl2();
        impl.add(10, 20);
    }

    /**
     * 多接口实现，当前类必须重写重名的默认方法
     */
    @Test
    public void doubleDefaultMethod1() {
        DefaultIntefaceImpl2 impl = new DefaultIntefaceImpl2();
        impl.div(20, 10);
    }

    /**
     * 类 和 接口默认方法的执行问题
     */
    @Test
    public void clazzExtendsWithDefaultIf() {
        ConcreteClazz concreteClazz = new ConcreteClazz();
        concreteClazz.add(10, 20);
    }

    /**
     * 实现默认接口
     */
    private class DefaultIntefaceImpl implements DefaultInterfaceExtends1 {

        @Override
        public void execute() {
        }
    }

    /**
     * 多个“最具体接口” 实现类必须重写，否则编译报错
     */
    private class DefaultIntefaceImpl2 implements DefaultInterfaceExtends1, DefaultInterfaceExtends2 {

        @Override
        public void execute() {
        }

        @Override
        public int add(int x, int y) {
            System.out.println("DefaultIntefaceImpl2");
            return 0;
        }
    }

    /**
     * 和 默认接口 的默认方法 同名的类
     */
    private class OtherClazz {

        public int add(int x, int y) {
            System.out.println("OtherClazz add()");
            return 0;
        }
    }

    /**
     * 继承的类 和 默认接口的默认方法 同名，调用时的规则：调用父类的方法
     */
    private class ConcreteClazz extends OtherClazz implements InterfaceDefaultMethod {

        @Override
        public void execute() {

        }
    }
}
