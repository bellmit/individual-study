package cn.gyw.corejava.initorder;

/**
 * 抽象类，父类
 */
public abstract class AbstractParentClz {

    protected String name = "AbstractParentClz";

    public AbstractParentClz() {
        tellName();
        printName();
    }

    protected abstract void tellName();

    public void printName() {
        System.out.println("AbstractParentClz print name : " + name);
    }
}
