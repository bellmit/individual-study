package cn.gyw.corejava.jol;

import cn.gyw.corejava.model.JolObject;
import org.openjdk.jol.info.ClassLayout;

/**
 * jol:java object layout java 对象布局工具
 *
 * -XX:+PrintFlagsInitial
 */
public class JolMain {

    private static JolObject obj;

    public static void main(String[] args) {

        obj = new JolObject();

        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
