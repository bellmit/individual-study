package cn.gyw.corejava.initorder;

public class SubClz1 extends ParentClz {

    public SubClz1() {
//        super();
        System.out.println("sub-clz");
    }

    /**
     * 静态方法不能被重写,而是隐藏
     */
//    @Override
    public static void show() {
        System.out.println("subclz-show");
    }

    @Override
    public String sayName() {
        return super.sayName();
    }
}
