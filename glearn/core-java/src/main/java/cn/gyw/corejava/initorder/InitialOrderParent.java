package cn.gyw.corejava.initorder;

/**
 * 类的初始化顺序
 *
 */
public class InitialOrderParent {
    // 静态变量
    private static String staticField = "parent-静态变量";
    // 普通变量
    private String nomalField = "parent-普通变量";
    // 静态代码块
    static {
        System.out.println(staticField);
        System.out.println("parent-静态初始化块");
    }
    // 普通代码块
    {
        System.out.println(nomalField);
        System.out.println("parent-普通代码块");
    }

    public InitialOrderParent() {
        System.out.println("parent-构造函数");
    }

}
