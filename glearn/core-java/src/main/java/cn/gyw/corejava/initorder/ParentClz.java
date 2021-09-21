package cn.gyw.corejava.initorder;

/**
 * 父类
 */
public class ParentClz {

    public ParentClz() {
        System.out.println("parent-clz");
    }

    public static void show() {
        System.out.println("parent-show");
    }

    public void ParentClz() {
        System.out.println("ParentClz method");
    }

    protected String sayName() throws NullPointerException {
        return "parent";
    }
}
