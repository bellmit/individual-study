package cn.gyw.corejava.internalclz;

import org.junit.Test;

/**
 * 嵌套类 测试
 *
 * 四种：
 * 1、静态成员类
 * 2、非静态成员类
 * 3、匿名类
 * 4、局部类
 */
public class InternalClazzTest {

    /**
     * 静态成员类
     */
    @Test
    public void staticClazz() {
        StaticClazz staticClazz = new StaticClazz();
        staticClazz.printOuterStr();

        StaticClazz.CustomClz customClz = new StaticClazz.CustomClz();
        customClz.printOuterStr();
    }

    /**
     * 非静态成员类
     */
    @Test
    public void nonStaticClazz() {
        // 创建方式1
        NonStaticClazz nonStaticClazz = new NonStaticClazz();
        NonStaticClazz.CustomClz customClz1 = nonStaticClazz.new CustomClz();
        nonStaticClazz.show();
        customClz1.printInner();
        // 创建方式1
        NonStaticClazz.CustomClz customClz2 = new NonStaticClazz().new CustomClz();
        customClz2.printInner();


    }
}
