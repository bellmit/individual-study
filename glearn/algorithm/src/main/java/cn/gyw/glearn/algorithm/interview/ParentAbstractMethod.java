package cn.gyw.glearn.algorithm.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * 子类调用父类的抽象方法
 *
 * 类的加载顺序
 *
 * Created by guanyw on 2019/2/18.
 */
public class ParentAbstractMethod extends ParentBase {

    private String name = "dervied";

    private List<String> list = new ArrayList<>(3);

    static {
        p = 12;
        System.out.println("-" + p);
    }

    protected ParentAbstractMethod() {
        tellName();
        printName();
    }

    @Override
    public void tellName() {
        System.out.println("list size :" + list.size());
        System.out.println("dervied tell name :" + name);
    }

    @Override
    public void printName() {
        System.out.println("dervied print name :" + name);
    }

    public static void main(String[] args) {
        new ParentAbstractMethod();
    }
}

abstract class ParentBase {

    static {
        p = 11;
    }

    static Integer p = 10;

    static {
        System.out.println(p);
    }

    {
        System.out.println("构造代码块");
    }

    protected String name = "base";

    ParentBase() {
        tellName();
        printName();
    }

    public abstract void tellName();

    public void printName() {
        System.out.println("Base print name :" + name);
    }

}
