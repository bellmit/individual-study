package cn.gyw.corejava.initorder;

public class InitialOrderSub extends InitialOrderParent {

    // 静态变量
    private static String staticField = "sub-静态变量";
    // 普通变量
    private String nomalField = "sub-普通变量";
    // 静态代码块
    static {
        System.out.println(staticField);
        System.out.println("sub-静态初始化块");
    }
    // 普通代码块
    {
        System.out.println(nomalField);
        System.out.println("sub-普通代码块");
    }

    public InitialOrderSub() {
        System.out.println("sub-构造函数");
    }

}
