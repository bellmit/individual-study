package cn.gyw.corejava.feature;

import org.junit.Test;

import cn.gyw.corejava.initorder.InitialOrderParent;
import cn.gyw.corejava.initorder.InitialOrderSub;
import cn.gyw.corejava.initorder.ParentClz;
import cn.gyw.corejava.initorder.SubClz1;
import cn.gyw.corejava.initorder.SubClz2;

/**
 * 继承特性的测试
 *
 * @see ParentClz
 * @see SubClz1
 */
public class ExtendsTest {

    /**
     * 子类初始化，构造函数的调用规则
     *
     * 结果：parent-clz
     * sub-clz
     */
    @Test
    public void extendsConstructMethod() {
        SubClz1 subClz = new SubClz1();
    }

    /**
     * 静态方法、静态属性
     * 可以被继承和隐藏，但不能重写，
     */
    @Test
    public void staticMethod() {
        ParentClz parentClz = new ParentClz();
    }

    /**
     * 抽象父类构造函数，调用子类的具体实现
     * 此时子类的name属性还未初始化，所以为null;
     * 结果：
     * p:SubClz2 tell name : null
     * p:SubClz2 print name : null
     * s:SubClz2 tell name : SubClz2
     * s:SubClz2 print name : SubClz2
     */
    @Test
    public void abstractMethod() {
        SubClz2 subClz2 = new SubClz2();
    }

    /**
     * 单类的初始化顺序
     * 静态变量-》静态代码块-》普通变量-》普通代码块-》构造函数
     */
    @Test
    public void initialOrderParentClz() {
        InitialOrderParent parent = new InitialOrderParent();
    }

    /**
     * 继承的子类初始化顺序
     * 父类静态变量、静态代码块 -》 子类静态变量、静态代码块
     * -》父类变量、普通代码块-》父类构造函数
     * -》子类变量、普通代码块-》子类构造函数
     */
    @Test
    public void initialOrderSubClz() {
        InitialOrderSub initialOrderSub = new InitialOrderSub();
    }
}
