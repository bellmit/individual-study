package cn.gyw.corejava.initorder;

public class SubClz2 extends AbstractParentClz {

    protected String name = "SubClz2";

    public SubClz2() {
        tellName();
        printName();
    }

    /**
     * 重写后的子类优先级高于父类优先级
     */
    @Override
    protected void tellName() {
        System.out.println("SubClz2 tell name : " + name);
    }

    @Override
    public void printName() {
        System.out.println("SubClz2 print name : " + name);
    }
}
